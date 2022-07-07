package com.dunnoWhatToEat.snapshot.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredienti_ricette")
public class IngredienteRicetta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredienti_ricette_id;
    @Column
    private double quantita;


    @Column
    private String unita_misura;
    @Column
    private String ricetta_id;



    public Long getIngredienti_ricette_id() {
        return ingredienti_ricette_id;
    }

    public void setIngredienti_ricette_id(Long ingredienti_ricette_id) {
        this.ingredienti_ricette_id = ingredienti_ricette_id;
    }

    public String getUnita_misura() {
        return unita_misura;
    }

    public void setUnita_misura(String unita_misura) {
        this.unita_misura = unita_misura;
    }

    public Ingrediente getIngrediente() {
        return ingrediente_id;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente_id = ingrediente;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="ingrediente_id")

    private Ingrediente ingrediente_id = new Ingrediente();


}
