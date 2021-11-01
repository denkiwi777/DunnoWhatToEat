package com.DunnoWhatToEat.v1.repository;

import com.DunnoWhatToEat.v1.Entity.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RicettaRepository extends JpaRepository<Ricetta, Long>,RicettaRepositoryCustom {

}
