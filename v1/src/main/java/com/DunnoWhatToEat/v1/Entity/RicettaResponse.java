package com.DunnoWhatToEat.v1.Entity;

public class RicettaResponse {
    private Ricetta ricetta;
    private Ingrediente ingrediente;

    public RicettaResponse(Ricetta ricetta, Ingrediente ingrediente) {
        this.ricetta = ricetta;
        this.ingrediente = ingrediente;
    }

    public RicettaResponse() {
    }

    public Ricetta getRicetta() {
        return ricetta;
    }

    public void setRicetta(Ricetta ricetta) {
        this.ricetta = ricetta;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
}
