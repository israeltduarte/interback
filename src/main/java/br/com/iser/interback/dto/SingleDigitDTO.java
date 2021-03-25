package br.com.iser.interback.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingleDigitDTO {

  private String number;

  private Integer multiplier;

  private String concatenated;

  private Integer result;

  private LocalDateTime createdAt;

}
