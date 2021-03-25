package br.com.iser.interback.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

  private static final long serialVersionUID = -5549395931742209066L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 2048)
  private String name;

  @Column(nullable = false, length = 2048)
  private String email;

  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL)
  private Key key;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private List<SingleDigit> singleDigits;

}
