package br.com.iser.interback.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.iser.interback.DefaultTest;
import br.com.iser.interback.constant.Constants;
import br.com.iser.interback.constant.Errors;
import br.com.iser.interback.dto.CustomerDTO;
import br.com.iser.interback.dto.CustomerRequestDTO;
import br.com.iser.interback.entity.Customer;
import br.com.iser.interback.exception.CustomerNotFoundException;
import br.com.iser.interback.mock.MockGenerator;
import br.com.iser.interback.repository.CustomerRepository;
import br.com.iser.interback.service.impl.CustomerServiceImpl;
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

public class CustomerServiceTests extends DefaultTest {

  @Autowired
  private CustomerServiceImpl customerService;

  @MockBean
  private CustomerRepository customerRepository;

  @Test
  public void getAllCustomersShouldReturnPageCustomers() {
    List<Customer> listCustomer = MockGenerator.getListCustomerWithoutSingleDigit();

    PageRequest page = PageRequest.of(0, Constants.PAGEABLE_SIZE);
    Page<Customer> pageCustomers = new PageImpl<>(listCustomer, page, listCustomer.size());

    when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageCustomers);

    Page<Customer> response = this.customerService.getCustomers(page);

    Assert.assertNotNull(response);
    Assert.assertEquals(Constants.PAGEABLE_SIZE, page.getPageSize());
  }

  @Test
  public void addCustomerShouldReturnCustomerDTO() {
    CustomerRequestDTO request = MockGenerator.getCustomerRequestDTO();
    Customer customer = MockGenerator.getCustomerWithoutSingleDigit();

    when(customerRepository.save(any())).thenReturn(customer);

    CustomerDTO response = this.customerService.addCustomer(request);

    Assert.assertNotNull(response);
    Assert.assertEquals(request.getName(), response.getName());
    Assert.assertEquals(request.getEmail(), response.getEmail());
  }

  @Test
  public void getCustomerShouldReturnCustomerDTO() {
    Customer customer = MockGenerator.getCustomerWithSingleDigit();

    when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));

    CustomerDTO response = this.customerService.getCustomer(1L);

    Assert.assertNotNull(response);
  }

  @Test
  public void getCustomerShouldThrowCustomerNotFound() {
    when(customerRepository.findById(any())).thenThrow(new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    assertThrows(CustomerNotFoundException.class, () -> this.customerService.getCustomer(1L));
  }

  @Test
  public void updateCustomerShouldReturnCustomerDTO() {
    CustomerRequestDTO request = MockGenerator.getCustomerRequestDTO();
    Customer customer = MockGenerator.getCustomerWithSingleDigit();

    when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
    when(customerRepository.save(any())).thenReturn(customer);

    CustomerDTO response = this.customerService.updateCustomer(1L, request);

    Assert.assertNotNull(response);
    Assert.assertEquals(request.getName(), response.getName());
    Assert.assertEquals(request.getEmail(), response.getEmail());
  }

  @Test
  public void updateCustomerShouldThrowCustomerNotFound() {
    CustomerRequestDTO request = MockGenerator.getCustomerRequestDTO();

    when(customerRepository.findById(any())).thenThrow(new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    assertThrows(CustomerNotFoundException.class, () -> this.customerService.updateCustomer(1L, request));
  }

  @Test
  public void deleteCustomerShouldReturnString() {
    Customer customer = MockGenerator.getCustomerWithSingleDigit();

    when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));

    this.customerService.deleteCustomer(1L);

    String response = this.customerService.deleteCustomer(1L);

    Assert.assertNotNull(response);
  }

  @Test
  public void deleteCustomerShouldThrowCustomerNotFound() {
    CustomerRequestDTO request = MockGenerator.getCustomerRequestDTO();

    when(customerRepository.findById(any())).thenThrow(new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    assertThrows(CustomerNotFoundException.class, () -> this.customerService.updateCustomer(1L, request));
  }

  @Test
  public void updateEncryptedCustomerShouldReturnCustomerDTO() {
    CustomerRequestDTO request = MockGenerator.getCustomerRequestDTO();
    Customer customer = MockGenerator.getEncryptedCustomer();

    when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));

    CustomerDTO response = this.customerService.updateCustomer(1L, request);

    Assert.assertNotNull(response);
  }
}
