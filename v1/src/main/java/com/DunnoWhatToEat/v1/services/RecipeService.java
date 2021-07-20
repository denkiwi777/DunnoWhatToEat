package com.DunnoWhatToEat.v1.services;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.DunnoWhatToEat.v1.Entity.Ricetta;

@Component
public class RecipeService {
	public static ArrayList<Ricetta> ricette = new ArrayList<>();


	public static ArrayList<Ricetta> getRicetta(){
		String[] ingredienti = {"cavolo", "pane"};

		return ricette;
	}


	public static Ricetta saveRicetta(Ricetta ricetta) {
		ricette.add(ricetta);
		return ricetta;
	}
}
