package com.DunnoWhatToEat.v1.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ingredienti")
public class Ingrediente {
    @Id
    @GeneratedValue
    @Column(name="ingrediente_id")
    private Long id;

    @Column(name="nome_ingrediente")
    private String nome;

    @Column
    private int quantita;

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public void setUnitaMisura(String unita_misura) {
        this.unita_misura = unita_misura;
    }

    public int getQuantita() {
        return quantita;
    }

    public String getUnitaMisura() {
        return unita_misura;
    }

    @Column
    private String unita_misura;

    @ManyToMany(mappedBy = "ingredienti")
    Set<Ricetta> ricetta = new HashSet<>();

    public Ingrediente() {
    }

    public Ingrediente(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Ingrediente(String ingre) {
        this.nome = ingre;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
