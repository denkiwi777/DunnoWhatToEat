package com.dunnoWhatToEat.snapshot.Entity;

import java.util.ArrayList;
import java.util.List;

public class RicettaResponse {
    private Ricetta ricetta;
    private Ingrediente ingredientePrincipale;

    public List<IngredienteResponse> getIngredientiResponse() {
        return ingredienti;
    }

    public void setIngredientiResponse(List<IngredienteResponse> ingredientiResponse) {
        this.ingredienti = ingredientiResponse;
    }

    private List<IngredienteResponse> ingredienti = new ArrayList<>();

    public RicettaResponse(Ricetta ricetta, Ingrediente ingrediente) {
        this.ricetta = ricetta;
        this.ingredientePrincipale = ingrediente;
    }

    public RicettaResponse() {
    }

    public Ricetta getRicetta() {
        return ricetta;
    }

    public void setRicetta(Ricetta ricetta) {
        this.ricetta = ricetta;
    }

    public Ingrediente getIngredientePrincipale() {
        return ingredientePrincipale;
    }

    public void setIngredientePrincipale(Ingrediente ingredientePrincipale) {
        this.ingredientePrincipale = ingredientePrincipale;
    }
    public void addIngrediente(IngredienteResponse ingrediente){
        this.ingredienti.add(ingrediente);
    }
}
