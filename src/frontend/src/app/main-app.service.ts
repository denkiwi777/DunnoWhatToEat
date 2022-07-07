import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MainAppService {

  constructor(private http: HttpClient) { }
  rootURL = 'http://localhost:8080/api';

  getRecipe() {
    return this.http.get(this.rootURL + '/GetRicetta');
  }

    addURecipe(recipe: any, id: number) {
	recipe.id = id;
	return this.http.post(this.rootURL + '/recipe', recipe);
  }

  searchRecipes(ingredients:string[]){
    return this.http.post(this.rootURL + '/search', ingredients);
}
}
