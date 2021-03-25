package br.com.iser.interback.dto;

import br.com.iser.interback.constant.Errors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingleDigitRequestDTO {

  private Long customerId;

  @NotBlank(message = Errors.VALIDATION_NUMBER)
  private String number;

  @NotNull(message = Errors.VALIDATION_MULTIPLIER)
  private Integer multiplier;

}
