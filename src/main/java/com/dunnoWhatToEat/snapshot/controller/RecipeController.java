package com.dunnoWhatToEat.snapshot.controller;

import java.util.List;


import com.dunnoWhatToEat.snapshot.Entity.Ricetta;
import com.dunnoWhatToEat.snapshot.Entity.RicettaResponse;
import com.dunnoWhatToEat.snapshot.repository.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/")
public class RecipeController {
	
	  @Autowired
	  RicettaRepository recipeService;
		
	    @GetMapping(path = "GetRicetta")
	    public ResponseEntity<?> listRicetta() {
	    	System.out.println("GET request");
	    	List<RicettaResponse> resource = recipeService.getRandomRecipes(10);
	        return ResponseEntity.ok(resource);
	    }
		
		@PostMapping(path = "PutRicetta")
		public ResponseEntity<?> saveRicetta(@RequestBody Ricetta ricetta) {
			System.out.println("PUT request");
			Ricetta resource = recipeService.save(ricetta);
	        return ResponseEntity.ok(resource);
	    }

	@PostMapping(path = "search")
	public ResponseEntity<?> searchRecipes(@RequestBody String[] ingredienti) {
		System.out.println("put request");
		List<RicettaResponse> resource = recipeService.search(ingredienti);
		return ResponseEntity.ok(resource);
	}

}
