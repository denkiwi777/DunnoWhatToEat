package com.DunnoWhatToEat.v1;

import com.DunnoWhatToEat.v1.Entity.Ingrediente;
import com.DunnoWhatToEat.v1.Entity.Ricetta;
import com.DunnoWhatToEat.v1.repository.IngredienteRepository;
import com.DunnoWhatToEat.v1.repository.RicettaRepository;
import com.DunnoWhatToEat.v1.utility.SesionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class V1ApplicationTests {
	@Autowired
	private static SesionCreator sessionFactory = new SesionCreator();
	private Session session;

	@Autowired
	private RicettaRepository ricettaRepository;
	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Test
	public void testRelazioneTabelle(){
		String[] ingredienti ={"farina", "latte", "uovo" ,"zucchero" ,"bacon", "formaggio"};
		String [] ricette = {"frittata", "torta"};


		for(String ricetta: ricette){
			Ricetta ricettaTemp = new Ricetta();
			ricettaTemp.setTitolo(ricetta);
			ricettaTemp.setPreparazione("Come preparare la ricetta" + ricetta);
			if(ricetta.equals("frittata")){
				Set<Ingrediente> ingr = new HashSet<>();
			for (String ingre : ingredienti){
					if(ingre.equals("uovo") || ingre.equals("bacon") || ingre.equals("formaggio")){

						Ingrediente ingredienteOb = new Ingrediente(ingre);
						ingredienteRepository.save(ingredienteOb);
						ingr.add(ingredienteOb);
						ricettaTemp.setIngrediente_princ(ingredienteOb.getId().intValue());					}
				}
			assertEquals(0, ricettaTemp.getIngredienti().size());
			ricettaTemp.setIngredienti(ingr);
				ricettaRepository.save(ricettaTemp);
			assertNotNull(ricettaTemp);
			}
			else{
				Set<Ingrediente> ingr = new HashSet<>();
				for (String ingre : ingredienti){
					if(ingre.equals("farina") || ingre.equals("latte") || ingre.equals("uova") || ingre.equals("zucchero")){

						Ingrediente ingredienteOb = new Ingrediente(ingre);
						ingredienteRepository.save(ingredienteOb);
						ingr.add(ingredienteOb);
						ricettaTemp.setIngrediente_princ(ingredienteOb.getId().intValue());

					}
				}
				assertEquals(0, ricettaTemp.getIngredienti().size());
				ricettaTemp.setIngredienti(ingr);

				ricettaRepository.save(ricettaTemp);


			}

		}
	}

	@Test
	public void getIngredFromRecipe(){
		@SuppressWarnings("unchecked")
		List<Ricetta> ricette  = session.createQuery("FROM ricette").list();
		assertNotNull(ricette);
		for(Ricetta ric :ricette){
		String res = 	ric.getIngredienti().toString();
		}
	}
	@Test
	void contextLoads() {
	}

}
