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
import br.com.iser.interback.entity.SingleDigit;
import br.com.iser.interback.exception.CustomerNotFoundException;
import br.com.iser.interback.mock.MockGenerator;
import br.com.iser.interback.service.CalculationService;
import br.com.iser.interback.service.CustomerService;
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

public class CalculationControllerTests extends DefaultTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CustomerService customerService;

  @MockBean
  private CalculationService calculationService;

  @Test
  public void getAllSingleDigitsReturnSingleDigits() throws Exception {
    String output = readJsonFile(PathsJSON.LIST_SINGLEDIGIT_RESPONSE);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(Paths.CALCULATIONS)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    PageRequest page = PageRequest.of(0, Constants.PAGEABLE_SIZE);
    List<SingleDigit> listSingleDigits = MockGenerator.getListSingleDigits();
    Page<SingleDigit> pageSingleDigits = new PageImpl<>(listSingleDigits, page, listSingleDigits.size());

    when(this.calculationService.getCalculations(any())).thenReturn(pageSingleDigits);

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
  public void addSingleDigitWithoutRegisteredEmailShouldThrowCustomerNotFound() throws Exception {
    String input = readJsonFile(PathsJSON.SINGLE_DIGIT_DTO_REQUEST);
    String output = readJsonFile(PathsJSON.ERROR_CUSTOMER_NOT_FOUND);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(Paths.CALCULATIONS)
        .content(input)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    when(this.calculationService.addCalculation(any())).thenThrow(new CustomerNotFoundException(Errors.CUSTOMER_NOT_FOUND));

    mvc.perform(requestBuilder).andExpect(status().isNotFound()).andExpect(content().json(output)).andReturn();
  }

  @Test
  public void addSingleDigitWithoutBodyShouldThrowBadRequest() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(Paths.CALCULATIONS)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    when(this.calculationService.addCalculation(any())).thenThrow(BadRequest.class);

    mvc.perform(requestBuilder).andExpect(status().isBadRequest()).andReturn();
  }
}
