package com.dunnoWhatToEat.snapshot;

import com.dunnoWhatToEat.snapshot.repository.IngredienteRepository;
import com.dunnoWhatToEat.snapshot.repository.RicettaRepository;
import com.dunnoWhatToEat.snapshot.utility.SesionCreator;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SnapshotApplication {
	@Autowired
	private static SesionCreator sessionFactory = new SesionCreator();
	private Session session;

	@Autowired
	public RicettaRepository ricettaRepository;
	@Autowired
	public IngredienteRepository ingredienteRepository;
	public static void main(String[] args) {
		SpringApplication.run(SnapshotApplication.class, args);
	}

	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/").allowedOrigins("http://localhost:8080");
			}
		};
	}

}
