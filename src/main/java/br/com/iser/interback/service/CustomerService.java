package br.com.iser.interback.service;

import br.com.iser.interback.dto.CustomerDTO;
import br.com.iser.interback.dto.CustomerRequestDTO;
import br.com.iser.interback.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

  Page<Customer> getCustomers(Pageable pageable);

  CustomerDTO addCustomer(CustomerRequestDTO dto);

  CustomerDTO getCustomer(Long id);

  CustomerDTO updateCustomer(Long id, CustomerRequestDTO dto);

  String deleteCustomer(Long id);

}
