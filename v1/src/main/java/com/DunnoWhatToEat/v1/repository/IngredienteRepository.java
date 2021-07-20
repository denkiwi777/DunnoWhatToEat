package com.DunnoWhatToEat.v1.repository;

import com.DunnoWhatToEat.v1.Entity.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
}
