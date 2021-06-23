package com.DunnoWhatToEat.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DunnoWhatToEat.v1.Entity.Ricetta;
import com.DunnoWhatToEat.v1.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/")
public class RecipeController {
	
	  @Autowired
	    RecipeService recipeService;
		
	    @GetMapping(path = "GetRicetta")
	    public ResponseEntity<?> listRicetta() {
	    	System.out.println("GET request");
	    	List<Ricetta> resource = RecipeService.getRicetta();
	        return ResponseEntity.ok(resource);
	    }
		
		@PostMapping(path = "PutRicetta")
		public ResponseEntity<?> saveRicetta(@RequestBody Ricetta ricetta) {
			System.out.println("PUT request");
			Ricetta resource = RecipeService.saveRicetta(ricetta);
	        return ResponseEntity.ok(resource);
	    }

}
