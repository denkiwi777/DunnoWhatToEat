package com.dunnoWhatToEat.snapshot.repository;

import com.dunnoWhatToEat.snapshot.Entity.Ingrediente;
import com.dunnoWhatToEat.snapshot.Entity.IngredienteRicetta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  IngredienteRicettaRepository extends JpaRepository<IngredienteRicetta, Long> {
}
