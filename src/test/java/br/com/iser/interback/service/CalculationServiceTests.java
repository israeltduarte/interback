package br.com.iser.interback.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.iser.interback.DefaultTest;
import br.com.iser.interback.constant.Constants;
import br.com.iser.interback.constant.Errors;
import br.com.iser.interback.dto.SingleDigitDTO;
import br.com.iser.interback.dto.SingleDigitRequestDTO;
import br.com.iser.interback.entity.Customer;
import br.com.iser.interback.entity.SingleDigit;
import br.com.iser.interback.exception.CustomerNotFoundException;
import br.com.iser.interback.mock.MockGenerator;
import br.com.iser.interback.repository.CalculationRepository;
import br.com.iser.interback.repository.CustomerRepository;
import br.com.iser.interback.service.impl.CalculationServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class CalculationServiceTests extends DefaultTest {

  @Autowired
  private CalculationServiceImpl calculationService;

  @MockBean
  private CalculationRepository calculationRepository;

  @MockBean
  private CustomerRepository customerRepository;

  @Test
  public void getAllCalculationsShouldReturnPageSingleDigits() {
    List<SingleDigit> listSingleDigits = MockGenerator.getListSingleDigits();

    PageRequest page = PageRequest.of(0, Constants.PAGEABLE_SIZE);
    Page<SingleDigit> pageCustomers = new PageImpl<>(listSingleDigits, page, listSingleDigits.size());

    when(calculationRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageCustomers);

    Page<SingleDigit> response = this.calculationService.getCalculations(page);

    Assert.assertNotNull(response);
  }

  @Test
  public void getCustomerCalculationsShouldReturnPageSingleDigitsDTO() {
    List<SingleDigit> listSingleDigits = MockGenerator.getListSingleDigits();

    PageRequest page = PageRequest.of(0, Constants.PAGEABLE_SIZE);
    Page<SingleDigit> pageSingleDigits = new PageImpl<>(listSingleDigits, page, listSingleDigits.size());

    when(calculationRepository.findAllByCustomerId(any(), Mockito.any(Pageable.class))).thenReturn(pageSingleDigits);

    Page<SingleDigitDTO> response = this.calculationService.getCustomerCalculations(1L, page);

    Assert.assertNotNull(response);
  }

  @Test
  public void addCustomerCalculationShouldReturnInteger() {
    SingleDigitRequestDTO request = MockGenerator.getSingleDigitRequestDTO();
    Customer customer = MockGenerator.getCustomerWithSingleDigit();
    SingleDigit singleDigit = MockGenerator.getSingleDigit();
    singleDigit.setCustomer(customer);

    when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
    when(calculationRepository.save(any())).thenReturn(singleDigit);

    Integer response = this.calculationService.addCalculation(request);

    Assert.assertNotNull(response);
  }

  @Test
  public void addCustomerCalculationRemovingCacheShouldReturnInteger() {
    SingleDigitRequestDTO request = MockGenerator.getSingleDigitRequestDTO();
    Customer customer = MockGenerator.getCustomerWithSingleDigit();
    SingleDigit singleDigit = MockGenerator.getSingleDigit();
    singleDigit.setCustomer(customer);

    when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
    when(calculationRepository.save(any())).thenReturn(singleDigit);

    Integer response = this.calculationService.addCalculation(request);

    Assert.assertNotNull(response);
  }

  @Test
  public void addCustomerCalculationShouldThrowCustomerNotFound() {
    SingleDigitRequestDTO request = MockGenerator.getSingleDigitRequestDTO();

    when(customerRepository.findById(any())).thenThrow(new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    assertThrows(CustomerNotFoundException.class, () -> this.calculationService.addCalculation(request));
  }
}
