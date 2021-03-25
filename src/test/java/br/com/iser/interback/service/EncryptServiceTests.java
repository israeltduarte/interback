package br.com.iser.interback.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.iser.interback.DefaultTest;
import br.com.iser.interback.constant.Errors;
import br.com.iser.interback.dto.CustomerDTO;
import br.com.iser.interback.entity.Customer;
import br.com.iser.interback.exception.CustomerNotFoundException;
import br.com.iser.interback.exception.EncryptingException;
import br.com.iser.interback.mock.MockGenerator;
import br.com.iser.interback.repository.CustomerRepository;
import br.com.iser.interback.service.impl.EncryptServiceImpl;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class EncryptServiceTests extends DefaultTest {

  @Autowired
  private EncryptServiceImpl encryptService;

  @MockBean
  private CustomerRepository customerRepository;

  @Test
  void encryptCustomerShouldReturnCustomerDTO() {
    String publicKeySent = MockGenerator.getPublicKey();
    Customer customer = MockGenerator.getCustomerWithSingleDigit();

    when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
    when(customerRepository.save(any())).thenReturn(customer);

    CustomerDTO response = this.encryptService.encryptCustomer(1L, publicKeySent);

    Assert.assertNotNull(response);
  }

  @Test
  void encryptCustomerShouldThrowCustomerNotFound() {
    String publicKeySent = MockGenerator.getPublicKey();

    when(customerRepository.findById(any())).thenThrow(new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    assertThrows(CustomerNotFoundException.class, () -> this.encryptService.encryptCustomer(1L, publicKeySent));
  }

  @Test
  void encryptCustomerShouldThrowAlreadyEncrypted() {
    String publicKeySent = MockGenerator.getPublicKey();
    Customer customer = MockGenerator.getEncryptedCustomer();

    when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));

    assertThrows(EncryptingException.class, () -> this.encryptService.encryptCustomer(1L, publicKeySent));
  }
}
