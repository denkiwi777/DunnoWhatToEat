package com.DunnoWhatToEat.v1;

import com.DunnoWhatToEat.v1.repository.IngredienteRepository;
import com.DunnoWhatToEat.v1.repository.RicettaRepository;
import com.DunnoWhatToEat.v1.utility.SesionCreator;
import databaseExtractor.RecipeExtractor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@SpringBootApplication
public class V1Application {
	@Autowired
	private static SesionCreator sessionFactory = new SesionCreator();
	private Session session;

	@Autowired
	public RicettaRepository ricettaRepository;
	@Autowired
	public IngredienteRepository ingredienteRepository;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(V1Application.class, args);

		RecipeExtractor rec = new RecipeExtractor();
		rec.run();
	}


}
