package hr.fer.progi.looneycodes.BytePit.api.repository;

import hr.fer.progi.looneycodes.BytePit.api.model.Nadmetanje;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * NadmetanjeRepository
 */
public interface NadmetanjeRepository extends JpaRepository<Nadmetanje, Integer> {
  /**
   * nadji nadmetanje na temelju indentifikatora
   */
  Nadmetanje findByNatjecanjeId(Integer natjecanjeId);
}
