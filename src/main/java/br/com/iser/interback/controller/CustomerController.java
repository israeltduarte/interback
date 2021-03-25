package br.com.iser.interback.controller;

import br.com.iser.interback.constant.Constants;
import br.com.iser.interback.constant.Paths;
import br.com.iser.interback.dto.CustomerDTO;
import br.com.iser.interback.dto.CustomerRequestDTO;
import br.com.iser.interback.dto.PublicKeyRequestDTO;
import br.com.iser.interback.dto.SingleDigitDTO;
import br.com.iser.interback.entity.Customer;
import br.com.iser.interback.service.CalculationService;
import br.com.iser.interback.service.CustomerService;
import br.com.iser.interback.service.EncryptService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = Paths.CUSTOMERS)
@Validated
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private CalculationService calculationService;

  @Autowired
  private EncryptService encryptService;

  @GetMapping
  public ResponseEntity<Page<Customer>> getCustomers(@PageableDefault(sort = Constants.PAGEABLE_SORT) Pageable pageable) {

    Page<Customer> customers = customerService.getCustomers(pageable);

    log.debug("CustomerController.getCustomers - End - Page<Customer>: {}", customers);

    return ResponseEntity.status(HttpStatus.OK).body(customers);
  }

  @PostMapping
  public ResponseEntity<CustomerDTO> addCustomer(@RequestBody @Valid CustomerRequestDTO dto) {

    CustomerDTO customerDTO = customerService.addCustomer(dto);

    log.debug("CustomerController.addCustomer - End - CustomerDTO: {}", customerDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);
  }

  @GetMapping(Paths.ID_CUSTOMER)
  public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("idCustomer") final Long id) {

    CustomerDTO customerDTO = customerService.getCustomer(id);

    log.debug("CustomerController.getCustomer - End - CustomerDTO: {}", customerDTO);

    return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
  }

  @PutMapping(Paths.ID_CUSTOMER)
  public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("idCustomer") final Long id,
      @RequestBody @Valid CustomerRequestDTO dto) {

    CustomerDTO customerDTO = customerService.updateCustomer(id, dto);

    log.debug("CustomerController.updateCustomer - End - CustomerDTO: {}", customerDTO);

    return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
  }

  @DeleteMapping(Paths.ID_CUSTOMER)
  public ResponseEntity<String> deleteCustomer(@PathVariable("idCustomer") final Long id) {

    String result = customerService.deleteCustomer(id);

    log.debug("CustomerController.deleteCustomer - End - String: {}", result);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PutMapping(Paths.ID_CUSTOMER + Paths.ENCRYPTING)
  public ResponseEntity<CustomerDTO> encryptCustomer(@PathVariable("idCustomer") final Long id,
      @RequestBody @Valid PublicKeyRequestDTO dto) {

    CustomerDTO customerDTO = encryptService.encryptCustomer(id, dto.getPublicKey());

    log.debug("CustomerController.encryptCustomerNameAndEmail - End - CustomerDTO: {}", customerDTO);

    return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
  }

  @GetMapping(Paths.ID_CUSTOMER + Paths.CALCULATIONS)
  public ResponseEntity<Page<SingleDigitDTO>> getCustomerCalculations(@PathVariable("idCustomer") final Long id,
      @PageableDefault(sort = Constants.PAGEABLE_SORT) Pageable pageable) {

    Page<SingleDigitDTO> singleDigits = calculationService.getCustomerCalculations(id, pageable);

    log.debug("CustomerController.getCustomerCalculations - End - List<SingleDigitDTO: {}", singleDigits);

    return ResponseEntity.status(HttpStatus.OK).body(singleDigits);
  }

}
