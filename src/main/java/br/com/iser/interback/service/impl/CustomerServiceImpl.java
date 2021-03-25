package br.com.iser.interback.service.impl;

import br.com.iser.interback.constant.Constants;
import br.com.iser.interback.constant.Errors;
import br.com.iser.interback.dto.CustomerDTO;
import br.com.iser.interback.dto.CustomerRequestDTO;
import br.com.iser.interback.entity.Customer;
import br.com.iser.interback.entity.Key;
import br.com.iser.interback.exception.CustomerNotFoundException;
import br.com.iser.interback.exception.EncryptingException;
import br.com.iser.interback.repository.CustomerRepository;
import br.com.iser.interback.service.CustomerService;
import br.com.iser.interback.service.EncryptService;
import com.sun.jersey.core.util.Base64;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private EncryptService encryptService;

  private final ModelMapper mapper;

  @Override
  public Page<Customer> getCustomers(Pageable pageable) {

    Page<Customer> customersPage = this.customerRepository.findAll(pageable);

    log.debug("CustomerServiceImpl.getCustomers - End - Page<Customer>: {}", customersPage);

    return customersPage;
  }

  @Override
  public CustomerDTO addCustomer(CustomerRequestDTO dto) {

    Key key = encryptService.generateKeys();

    Customer customer = Customer.builder().key(key).name(dto.getName()).email(dto.getEmail()).build();

    this.customerRepository.save(customer);

    CustomerDTO customerDTO = this.mapper.map(customer, CustomerDTO.class);

    log.debug("CustomerServiceImpl.addCustomer - End - CustomerDTO: {}", customerDTO);

    return customerDTO;
  }

  @Override
  public CustomerDTO getCustomer(Long id) {

    Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    if (Boolean.TRUE.equals(customer.getKey().getIsActive())) {
      this.decryptCustomer(customer);
    }

    CustomerDTO customerDTO = this.mapper.map(customer, CustomerDTO.class);

    log.debug("CustomerServiceImpl.getCustomer - End - CustomerDTO: {}", customerDTO);

    return customerDTO;
  }

  @Override
  public CustomerDTO updateCustomer(Long id, CustomerRequestDTO dto) {

    Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    if (Boolean.TRUE.equals(customer.getKey().getIsActive())) {
      this.updateEncryptedCustomer(dto, customer);
    } else {
      this.updateDecryptedCustomer(dto, customer);
    }

    CustomerDTO customerDTO = this.mapper.map(customer, CustomerDTO.class);

    log.debug("CustomerServiceImpl.updateCustomer - End - CustomerDTO: {}", customerDTO);

    return customerDTO;
  }

  @Override
  public String deleteCustomer(Long id) {

    Customer customer = this.customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    this.customerRepository.delete(customer);

    String result = Constants.DELETED + customer.getName();

    log.debug("CustomerServiceImpl.deleteCustomer - End - String: {}", result);

    return result;
  }

  private void updateDecryptedCustomer(CustomerRequestDTO dto, Customer customer) {

    customer.setName(dto.getName());
    customer.setEmail(dto.getEmail());

    this.customerRepository.save(customer);
  }

  private void updateEncryptedCustomer(CustomerRequestDTO dto, Customer customer) {

    this.decryptCustomer(customer);

    customer.setName(dto.getName());
    customer.setEmail(dto.getEmail());

    this.encryptCustomer(customer);

    this.customerRepository.save(customer);

    this.decryptCustomer(customer);
  }

  private void encryptCustomer(Customer customer) {

    String publicKeyString = customer.getKey().getPublicKey();

    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString.getBytes()));

    try {

      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PublicKey publicKey = keyFactory.generatePublic(keySpec);

      customer.setName(this.encryptService.encrypt(customer.getName(), publicKey));
      customer.setEmail(this.encryptService.encrypt(customer.getEmail(), publicKey));
    } catch (Exception e) {
      throw new EncryptingException(Errors.ENCRYPTING);
    }
  }

  private void decryptCustomer(Customer customer) {

    String privateKeyString = customer.getKey().getPrivateKey();

    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString.getBytes()));

    try {

      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

      customer.setName(this.encryptService.decrypt(customer.getName(), privateKey));
      customer.setEmail(this.encryptService.decrypt(customer.getEmail(), privateKey));
    } catch (Exception e) {
      throw new EncryptingException(Errors.ENCRYPTING);
    }
  }
}