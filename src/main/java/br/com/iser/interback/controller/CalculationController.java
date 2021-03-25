package br.com.iser.interback.controller;

import br.com.iser.interback.constant.Constants;
import br.com.iser.interback.constant.Paths;
import br.com.iser.interback.dto.SingleDigitRequestDTO;
import br.com.iser.interback.entity.SingleDigit;
import br.com.iser.interback.service.CalculationService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = Paths.CALCULATIONS)
@Validated
public class CalculationController {

  @Autowired
  private CalculationService calculationService;

  @GetMapping
  public ResponseEntity<Page<SingleDigit>> getCalculations(@PageableDefault(sort = Constants.PAGEABLE_SORT) Pageable pageable) {

    Page<SingleDigit> singleDigitPage = calculationService.getCalculations(pageable);

    log.debug("CalculationController.getCalculations - End - Page<SingleDigit>: {}", singleDigitPage);

    return ResponseEntity.status(HttpStatus.OK).body(singleDigitPage);
  }

  @PostMapping
  public ResponseEntity<Integer> addCalculation(@RequestBody @Valid SingleDigitRequestDTO dto) {

    Integer result = calculationService.addCalculation(dto);

    log.debug("CalculationController.addCalculation - End - Integer: {}", result);

    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }
}
