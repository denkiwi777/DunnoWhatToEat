package com.dunnoWhatToEat.snapshot.Entity;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ricette")
public class Ricetta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ricetta_id;

	@Override
	public String toString() {
		return "Ricetta{" +
				"ricetta_id=" + ricetta_id +
				", titolo='" + titolo + '\'' +
				", tipo_piatto=" + tipo_piatto +
				", ingrediente_princ=" + ingrediente_princ +
				", nr_persone=" + nr_persone +
				", note='" + note + '\'' +
				", preparazione='" + preparazione + '\'' +
				", ingredienti=" + ingredienti +
				'}';
	}

	@Column
	public String titolo;

	@Column
	public int tipo_piatto;

	@Column
	public Long ingrediente_princ;

	@Column
	public int nr_persone;

	@Column
	public String note;

	@Column
	@Type(type = "text")
	public String preparazione;


	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
			name="ingredienti_ricette",
			joinColumns = {@JoinColumn(name="ingredienti_ricette_id")}
	)
	private Set<IngredienteRicetta> ingredienti = new HashSet<>();



	public Ricetta() {
	}

	public Ricetta(Long ricetta_id, String titolo, int tipo_piatto, Long ingrediente_princ, int nr_persone, String note, String preparazione, Set<IngredienteRicetta> ingredienti) {
		this.ricetta_id = ricetta_id;
		this.titolo = titolo;
		this.tipo_piatto = tipo_piatto;
		this.ingrediente_princ = ingrediente_princ;
		this.nr_persone = nr_persone;
		this.note = note;
		this.preparazione = preparazione;
		this.ingredienti = ingredienti;
	}


	public void setRicetta_id(Long ricetta_id) {
		this.ricetta_id = ricetta_id;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public void setTipo_piatto(int tipo_piatto) {
		this.tipo_piatto = tipo_piatto;
	}

	public void setIngrediente_princ(Long ingrediente_princ) {
		this.ingrediente_princ = ingrediente_princ;
	}

	public void setNr_persone(int nr_persone) {
		this.nr_persone = nr_persone;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setPreparazione(String preparazione) {
		this.preparazione = preparazione;
	}

	public void setIngredienti(Set<IngredienteRicetta> ingredienti) {
		this.ingredienti = ingredienti;
	}

	public Long getRicetta_id() {
		return ricetta_id;
	}

	public String getTitolo() {
		return titolo;
	}

	public int getTipo_piatto() {
		return tipo_piatto;
	}

	public Long getIngrediente_princ() {
		return ingrediente_princ;
	}

	public int getNr_persone() {
		return nr_persone;
	}

	public String getNote() {
		return note;
	}

	public String getPreparazione() {
		return preparazione;
	}

	public Set<IngredienteRicetta> getIngredienti() {
		return ingredienti;
	}
}
