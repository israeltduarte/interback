package br.com.iser.interback.dto;

import br.com.iser.interback.constant.Errors;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

  @NotBlank(message = Errors.VALIDATION_NAME)
  private String name;

  @NotBlank(message = Errors.VALIDATION_EMAIL)
  private String email;

}
