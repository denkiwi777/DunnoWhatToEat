package com.DunnoWhatToEat.v1.Entity;


public class Ricetta {
public String descrizione;
public String[] ingredienti;
public Ricetta(String descrizione, String[]  ingredienti) {
	super();
	this.descrizione = descrizione;
	this.ingredienti = ingredienti;
}
public String getDescrizione() {
	return descrizione;
}
public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
}
public String[] getIngredienti() {
	return ingredienti;
}
public void setIngredienti(String[] ingredienti) {
	this.ingredienti = ingredienti;
}



}
