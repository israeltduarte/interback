package br.com.iser.interback.service;

import br.com.iser.interback.dto.SingleDigitDTO;
import br.com.iser.interback.dto.SingleDigitRequestDTO;
import br.com.iser.interback.entity.SingleDigit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CalculationService {

  Page<SingleDigit> getCalculations(Pageable pageable);

  Page<SingleDigitDTO> getCustomerCalculations(Long id, Pageable pageable);

  Integer addCalculation(SingleDigitRequestDTO dto);

}
