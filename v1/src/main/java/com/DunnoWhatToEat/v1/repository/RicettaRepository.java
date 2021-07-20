package com.DunnoWhatToEat.v1.repository;

import com.DunnoWhatToEat.v1.Entity.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RicettaRepository extends JpaRepository<Ricetta, Long> {
}
