package com.dunnoWhatToEat.snapshot.Entity;


import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ingredienti_ricette")
public class IngredienteRicetta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ingredienti_ricette_id")
    private Long ingredienti_ricette_id;

    @Column
    private double quantita;


    @Column
    private String unita_misura;

    @ManyToOne
    @JoinColumn(name ="ricetta_id")
    private Ricetta ricetta;

    @ManyToOne
    @JoinColumn(name ="ingrediente_id")
    private Ingrediente ingrediente;


    public Long getIngredienti_ricette_id() {
        return ingredienti_ricette_id;
    }

    public void setIngredienti_ricette_id(Long ingredienti_ricette_id) {
        this.ingredienti_ricette_id = ingredienti_ricette_id;
    }

    public double getQuantita() {
        return quantita;
    }

    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }

    public String getUnitaMisura() {
        return unita_misura;
    }

    public void setUnitaMisura(String unita_misura) {
        this.unita_misura = unita_misura;
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
