package com.dunnoWhatToEat.snapshot.repository;


import java.util.List;

interface RicettaRepositoryCustom {

     List getRandomRecipes(int numberOfRecipes);

     List search(String[] ingredients);
}
