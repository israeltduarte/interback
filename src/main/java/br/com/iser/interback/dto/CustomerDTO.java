package br.com.iser.interback.dto;

import br.com.iser.interback.entity.SingleDigit;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

  private String name;

  private String email;

  private List<SingleDigit> singleDigits;

  private String publicKey;

}
