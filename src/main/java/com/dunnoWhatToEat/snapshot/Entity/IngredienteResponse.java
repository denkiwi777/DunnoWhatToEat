package com.dunnoWhatToEat.snapshot.Entity;

import java.math.BigInteger;

public class IngredienteResponse {
    public IngredienteResponse() {
    }
    private Long ingredienteId;
    public IngredienteResponse(String nome, double quantita, String unitaMisura) {
        this.nome = nome;
        this.quantita = quantita;
        this.unitaMisura = unitaMisura;
    }

    public Long getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(Long ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getQuantita() {
        return quantita;
    }

    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }

    public String getUnitaMisura() {
        return unitaMisura;
    }

    public void setUnitaMisura(String unitaMisura) {
        this.unitaMisura = unitaMisura;
    }

    private String nome;
    private double quantita;
    private String unitaMisura;
}
