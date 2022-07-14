package com.dunnoWhatToEat.snapshot.Entity;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

 @Entity
@Table(name="ingredienti")
public class Ingrediente {
    @Id
    @GeneratedValue
    @Column(name="ingrediente_id")
    private Long id;

    @Column(name="nome_ingrediente", unique = true)
    private String nome;





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
