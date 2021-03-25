package br.com.iser.interback.repository;

import br.com.iser.interback.entity.SingleDigit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationRepository extends JpaRepository<SingleDigit, Long> {

  Page<SingleDigit> findAllByCustomerId(Long id, Pageable pageable);

}
