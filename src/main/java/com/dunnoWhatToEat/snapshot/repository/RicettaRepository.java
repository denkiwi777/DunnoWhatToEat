package com.dunnoWhatToEat.snapshot.repository;

import com.dunnoWhatToEat.snapshot.Entity.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RicettaRepository extends JpaRepository<Ricetta, Long>,RicettaRepositoryCustom {

}
