package br.com.iser.interback.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.iser.interback.DefaultTest;
import br.com.iser.interback.constant.Constants;
import br.com.iser.interback.constant.Errors;
import br.com.iser.interback.constant.Paths;
import br.com.iser.interback.constant.PathsJSON;
import br.com.iser.interback.dto.CustomerDTO;
import br.com.iser.interback.dto.SingleDigitDTO;
import br.com.iser.interback.entity.Customer;
import br.com.iser.interback.exception.CustomerNotFoundException;
import br.com.iser.interback.exception.ValidationErrorException;
import br.com.iser.interback.mock.MockGenerator;
import br.com.iser.interback.service.CalculationService;
import br.com.iser.interback.service.CustomerService;
import br.com.iser.interback.service.EncryptService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.MethodNotAllowed;

public class CustomerControllerTests extends DefaultTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CustomerService customerService;

  @MockBean
  private CalculationService calculationService;

  @MockBean
  private EncryptService encryptService;

  @Test
  public void getAllCustomersShouldReturnCustomers() throws Exception {
    String output = readJsonFile(PathsJSON.LIST_CUSTOMERS_RESPONSE);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(Paths.CUSTOMERS)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    PageRequest page = PageRequest.of(0, Constants.PAGEABLE_SIZE);
    List<Customer> listCustomers = MockGenerator.getListCustomerWithSingleDigit();
    Page<Customer> pageCustomers = new PageImpl<>(listCustomers, page, listCustomers.size());

    when(this.customerService.getCustomers(any())).thenReturn(pageCustomers);

    mvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().json(output)).andReturn();
  }

  @Test
  public void addSingleDigitReturnSingleDigitDTO() throws Exception {
    String input = readJsonFile(PathsJSON.SINGLE_DIGIT_DTO_REQUEST);
    String output = readJsonFile(PathsJSON.SINGLE_DIGIT_DTO_RESPONSE);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(Paths.CALCULATIONS)
        .content(input)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    when(this.calculationService.addCalculation(any())).thenReturn(6);

    mvc.perform(requestBuilder).andExpect(status().isCreated()).andExpect(content().json(output)).andReturn();
  }

  @Test
  public void addCustomerShouldReturnCustomerDTO() throws Exception {
    String input = readJsonFile(PathsJSON.CUSTOMER_REQUEST);
    String output = readJsonFile(PathsJSON.CUSTOMERDTO_RESPONSE);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(Paths.CUSTOMERS)
        .content(input)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    CustomerDTO customerDTO = MockGenerator.getCustomerDTOWithSingleDigit();

    when(this.customerService.addCustomer(any())).thenReturn(customerDTO);

    mvc.perform(requestBuilder).andExpect(status().isCreated()).andExpect(content().json(output)).andReturn();
  }

  @Test
  public void addCustomerWithoutBodyShouldThrowBadRequest() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(Paths.CUSTOMERS)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    mvc.perform(requestBuilder).andExpect(status().isBadRequest()).andReturn();
  }

  @Test
  public void addCustomerWithInvalidEmailShouldThrowValidationError() throws Exception {
    String input = readJsonFile(PathsJSON.CUSTOMER_REQUEST);
    String output = readJsonFile(PathsJSON.ERROR_UNIQUE_EMAIL);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(Paths.CUSTOMERS)
        .accept(MediaType.APPLICATION_JSON)
        .content(input)
        .contentType(MediaType.APPLICATION_JSON);

    when(customerService.addCustomer(any())).thenThrow(new ValidationErrorException(Errors.UNIQUE_EMAIL));

    mvc.perform(requestBuilder).andExpect(status().isBadRequest()).andExpect(content().json(output)).andReturn();
  }

  @Test
  public void getCustomerShouldReturnCustomerDTO() throws Exception {
    String output = readJsonFile(PathsJSON.CUSTOMERDTO_RESPONSE);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(Paths.CUSTOMERS + Paths.ID_CUSTOMER.replace(Constants.ID_CUSTOMER, "1"))
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    CustomerDTO customerDTO = MockGenerator.getCustomerDTOWithSingleDigit();

    when(this.customerService.getCustomer(any())).thenReturn(customerDTO);

    mvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().json(output)).andReturn();
  }

  @Test
  public void getCustomerShouldThrowCustomerNotFound() throws Exception {
    String output = readJsonFile(PathsJSON.ERROR_CUSTOMER_NOT_FOUND);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(Paths.CUSTOMERS + Paths.ID_CUSTOMER.replace(Constants.ID_CUSTOMER, "123"))
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    when(this.customerService.getCustomer(any())).thenThrow(new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    mvc.perform(requestBuilder).andExpect(status().isNotFound()).andExpect(content().json(output)).andReturn();
  }

  @Test
  public void updateCustomerShouldReturnCustomerDTO() throws Exception {
    String input = readJsonFile(PathsJSON.CUSTOMER_REQUEST);
    String output = readJsonFile(PathsJSON.CUSTOMERDTO_RESPONSE);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .put(Paths.CUSTOMERS + Paths.ID_CUSTOMER.replace(Constants.ID_CUSTOMER, "1"))
        .accept(MediaType.APPLICATION_JSON)
        .content(input)
        .contentType(MediaType.APPLICATION_JSON);

    CustomerDTO customerDTO = MockGenerator.getCustomerDTOWithSingleDigit();

    when(this.customerService.updateCustomer(any(), any())).thenReturn(customerDTO);

    mvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().json(output)).andReturn();
  }

  @Test
  public void updateCustomerShouldThrowCustomerNotFound() throws Exception {
    String input = readJsonFile(PathsJSON.CUSTOMER_REQUEST);
    String output = readJsonFile(PathsJSON.ERROR_CUSTOMER_NOT_FOUND);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .put(Paths.CUSTOMERS + Paths.ID_CUSTOMER.replace(Constants.ID_CUSTOMER, "123"))
        .content(input)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    when(this.customerService.updateCustomer(any(), any())).thenThrow(new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    mvc.perform(requestBuilder).andExpect(status().isNotFound()).andExpect(content().json(output)).andReturn();
  }

  @Test
  public void updateCustomerShouldThrowMethodNotAllowed() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .put(Paths.CUSTOMERS + Paths.ID_CUSTOMER.replace(Constants.ID_CUSTOMER, ""))
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    when(this.customerService.getCustomer(any())).thenThrow(MethodNotAllowed.class);

    mvc.perform(requestBuilder).andExpect(status().isMethodNotAllowed()).andReturn();
  }

  @Test
  public void deleteCustomerShouldReturnString() throws Exception {
    String output = readJsonFile(PathsJSON.DELETED_STRING_RESPONSE);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .delete(Paths.CUSTOMERS + Paths.ID_CUSTOMER.replace(Constants.ID_CUSTOMER, "1"))
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    String response = "Customer deleted: Gabrielle Almeida";

    when(this.customerService.deleteCustomer(any())).thenReturn(response);

    mvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().string(output)).andReturn();
  }

  @Test
  public void getCustomerCalculationsShouldReturnSingleDigits() throws Exception {
    String output = readJsonFile(PathsJSON.LIST_SINGLEDIGITDTO_RESPONSE);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(Paths.CUSTOMERS + Paths.ID_CUSTOMER.replace(Constants.ID_CUSTOMER, "1") + Paths.CALCULATIONS)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    PageRequest page = PageRequest.of(0, Constants.PAGEABLE_SIZE);
    List<SingleDigitDTO> listSingleDigitsDTO = MockGenerator.getListSingleDigitsDTO();
    Page<SingleDigitDTO> pageSingleDigitDTO = new PageImpl<>(listSingleDigitsDTO, page, listSingleDigitsDTO.size());

    when(this.calculationService.getCustomerCalculations(any(), any())).thenReturn(pageSingleDigitDTO);

    mvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(content().json(output)).andReturn();
  }

  @Test
  public void getCustomerCalculationsShouldThrowBadRequest() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(Paths.CUSTOMERS + Paths.ID_CUSTOMER.replace(Constants.ID_CUSTOMER, "") + Paths.CALCULATIONS)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    when(this.calculationService.getCustomerCalculations(any(), any())).thenThrow(BadRequest.class);

    mvc.perform(requestBuilder).andExpect(status().isBadRequest()).andReturn();
  }

  @Test
  public void encryptCustomerShouldReturnCustomerDTO() throws Exception {
    String input = readJsonFile(PathsJSON.PUBLIC_KEY_DTO_REQUEST);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .put(Paths.CUSTOMERS + Paths.ID_CUSTOMER.replace(Constants.ID_CUSTOMER, "1") + Paths.ENCRYPTING)
        .content(input)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    CustomerDTO customerDTO = MockGenerator.getEncryptedCustomerDTO();

    when(this.encryptService.encryptCustomer(any(), any())).thenReturn(customerDTO);

    mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
  }

  @Test
  public void encryptCustomerShouldThrowCustomerNotFound() throws Exception {
    String input = readJsonFile(PathsJSON.PUBLIC_KEY_DTO_REQUEST);
    String output = readJsonFile(PathsJSON.ERROR_CUSTOMER_NOT_FOUND);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .put(Paths.CUSTOMERS + Paths.ID_CUSTOMER.replace(Constants.ID_CUSTOMER, "123") + Paths.ENCRYPTING)
        .content(input)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    when(this.encryptService.encryptCustomer(any(), any())).thenThrow(new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    mvc.perform(requestBuilder).andExpect(status().isNotFound()).andExpect(content().json(output)).andReturn();
  }
}
