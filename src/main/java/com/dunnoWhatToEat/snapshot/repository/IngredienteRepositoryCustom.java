package com.dunnoWhatToEat.snapshot.repository;

import com.dunnoWhatToEat.snapshot.Entity.Ingrediente;

public interface IngredienteRepositoryCustom {
    public Ingrediente getByName(String name);
    public  Ingrediente getByID(Long id);
}
