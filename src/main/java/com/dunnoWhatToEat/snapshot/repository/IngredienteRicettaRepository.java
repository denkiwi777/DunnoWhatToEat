package com.dunnoWhatToEat.snapshot.repository;

import com.dunnoWhatToEat.snapshot.Entity.Ingrediente;
import com.dunnoWhatToEat.snapshot.Entity.IngredienteRicetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  IngredienteRicettaRepository extends JpaRepository<IngredienteRicetta, Long> {
}
