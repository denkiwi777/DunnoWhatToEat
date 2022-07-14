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
	@Column(name="ricetta_id")
	private Long ricetta_id;
	@Column
	public String titolo;

	@Column
	public int tipo_piatto;


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ingr_principale", referencedColumnName="ingrediente_id")
	public Ingrediente ingrediente_princ;



	@Column
	public int nr_persone;

	@Column
	public String note;

	@Column
	@Type(type = "text")
	public String preparazione;


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
				'}';
	}

	public Long getRicetta_id() {
		return ricetta_id;
	}

	public void setRicetta_id(Long ricetta_id) {
		this.ricetta_id = ricetta_id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public int getTipo_piatto() {
		return tipo_piatto;
	}

	public void setTipo_piatto(int tipo_piatto) {
		this.tipo_piatto = tipo_piatto;
	}

	public Ingrediente getIngrediente_princ() {
		return ingrediente_princ;
	}

	public void setIngrediente_princ(Ingrediente ingrediente_princ) {
		this.ingrediente_princ = ingrediente_princ;
	}

	public int getNr_persone() {
		return nr_persone;
	}

	public void setNr_persone(int nr_persone) {
		this.nr_persone = nr_persone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPreparazione() {
		return preparazione;
	}

	public void setPreparazione(String preparazione) {
		this.preparazione = preparazione;
	}
}
