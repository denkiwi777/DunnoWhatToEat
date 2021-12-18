package com.DunnoWhatToEat.v1.repository;

import com.DunnoWhatToEat.v1.Entity.Ingrediente;
import com.DunnoWhatToEat.v1.Entity.Ricetta;
import com.DunnoWhatToEat.v1.Entity.RicettaResponse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RicettaRepositoryImpl implements RicettaRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List getRandomRecipes(int numberOfRecipes) {
        List<RicettaResponse> ricette = new ArrayList<>();
        while(ricette.size()<=numberOfRecipes){
            double randomNum =(Math.random() /Math.random())*  (Math.floor((Math.random()+10)))*(Math.random() /Math.random())*(Math.random() /Math.random())*(Math.floor((Math.random()+10)))*(Math.random() /Math.random());
            if(randomNum>=1 && randomNum<=28198){
                Long randomId = Math.round(randomNum);
                Ricetta ricetta = em.find(Ricetta.class, randomId);
                RicettaResponse response = new RicettaResponse();
                response.setRicetta(ricetta);
               List<Ingrediente> arr= ricetta.getIngredienti().stream().filter(
                       res->res.getId().equals(ricetta.getIngrediente_princ())).collect(Collectors.toList());
                for (Ingrediente ingPrinc: arr
                     ) {
                        response.setIngrediente(ingPrinc);
                    }
                ricette.add(response);
            }
        }
        return ricette;
    }
}
