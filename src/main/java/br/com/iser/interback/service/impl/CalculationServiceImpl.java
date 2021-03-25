package br.com.iser.interback.service.impl;

import br.com.iser.interback.constant.Errors;
import br.com.iser.interback.dto.SingleDigitDTO;
import br.com.iser.interback.dto.SingleDigitRequestDTO;
import br.com.iser.interback.entity.Customer;
import br.com.iser.interback.entity.SingleDigit;
import br.com.iser.interback.exception.CustomerNotFoundException;
import br.com.iser.interback.repository.CalculationRepository;
import br.com.iser.interback.repository.CustomerRepository;
import br.com.iser.interback.service.CacheService;
import br.com.iser.interback.service.CalculationService;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Data
public class CalculationServiceImpl implements CalculationService {

  @Autowired
  private CalculationRepository calculationRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private final CacheService cacheService;

  private final ModelMapper mapper;

  @Override
  public Page<SingleDigit> getCalculations(Pageable pageable) {

    Page<SingleDigit> singleDigitPage = this.calculationRepository.findAll(pageable);

    log.debug("CalculationServiceImpl.getCalculations - End - Page<SingleDigit>: {}", singleDigitPage);

    return singleDigitPage;
  }

  @Override
  public Page<SingleDigitDTO> getCustomerCalculations(Long customerId, Pageable pageable) {

    Page<SingleDigit> singleDigitPage = this.calculationRepository.findAllByCustomerId(customerId, pageable);

    Page<SingleDigitDTO> singleDigitDTOPage = singleDigitPage.map(singlePage -> this.mapper.map(singlePage, SingleDigitDTO.class));

    log.debug("CalculationServiceImpl.getCustomerCalculations - End - Page<SingleDigitDTO>: {}", singleDigitDTOPage);

    return singleDigitDTOPage;
  }

  @Override
  public Integer addCalculation(SingleDigitRequestDTO dto) {

    Customer customer = null;

    if (null != dto.getCustomerId()) {
      customer = this.customerRepository.findById(dto.getCustomerId())
          .orElseThrow(() -> new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));
    }

    SingleDigit singleDigit = this.generateSingleDigit(dto, customer);

    Integer result = singleDigit.getResult();

    this.calculationRepository.save(singleDigit);

    log.debug("CalculationServiceImpl.addCalculation - End - Integer: {}", result);

    return result;
  }

  private void manageCache(String concatenated, Integer result) {

    if (Boolean.FALSE.equals(this.cacheService.validateEntry())) {
      this.cacheService.remove(concatenated);
    }

    this.cacheService.insert(concatenated, result);
  }

  private SingleDigit generateSingleDigit(SingleDigitRequestDTO dto, Customer customer) {

    SingleDigit singleDigit = SingleDigit.builder().multiplier(dto.getMultiplier()).number(dto.getNumber()).build();

    if (null != customer) {
      singleDigit.setCustomer(customer);
    }

    singleDigit.setCreatedAt(LocalDateTime.now());

    String concatenated = this.concatenateNumber(dto.getMultiplier(), dto.getNumber());

    singleDigit.setConcatenated(concatenated);

    singleDigit.setResult(this.generateResult(concatenated));

    return singleDigit;
  }

  private String concatenateNumber(Integer multiplier, String number) {

    StringBuilder concatenation = new StringBuilder();

    for (int i = 0; i < multiplier; i++) {
      concatenation.append(number);
    }

    return concatenation.toString();
  }

  private Integer generateResult(String concatenated) {

    Integer result = cacheService.get(concatenated);
    if (result == null) {

      result = this.calculate(concatenated);

      this.manageCache(concatenated, result);
    }

    return result;
  }

  private Integer calculate(String number) {

    int result = 0;

    for (int i = 0; i < number.length(); i++) {
      result += Character.getNumericValue(number.charAt(i));
    }

    if (result < 10) {
      return result;
    }

    return this.calculate(String.valueOf(result));
  }
}
