package com.dunnoWhatToEat.snapshot.repository;

import com.dunnoWhatToEat.snapshot.Entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
}
