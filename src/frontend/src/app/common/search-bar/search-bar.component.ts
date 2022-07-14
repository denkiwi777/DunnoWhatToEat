import { MainAppService } from './../../main-app.service';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import {MatChipInputEvent} from '@angular/material/chips';
import {COMMA, ENTER} from '@angular/cdk/keycodes';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {
  @Output() searchEventEmitter = new EventEmitter<any>();

  constructor(private service :MainAppService) { }

  ngOnInit(): void {
  }
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  ingredientsKeywords: string[] = [];

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.ingredientsKeywords.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove(value:string): void {
    const index = this.ingredientsKeywords.indexOf(value);

    if (index >= 0) {
      this.ingredientsKeywords.splice(index, 1);
    }
  }
  search(){
    const subs = this.service.searchRecipes(this.ingredientsKeywords);
    subs.subscribe((data:any )=>{
      console.log(data);
      this.searchEventEmitter.emit(data)

    });
  }
  searchFewIngredients(){
    const subs = this.service.searchRecipesFewIngr(this.ingredientsKeywords);
    subs.subscribe((data:any )=>{
      console.log(data);
      this.searchEventEmitter.emit(data)

    });
  }
  searchLike(){
    const subs = this.service.searchRecipesLike(this.ingredientsKeywords);
    subs.subscribe((data:any )=>{
      console.log(data);
      this.searchEventEmitter.emit(data)

    });
  }
}
