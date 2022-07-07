import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgxMasonryComponent, NgxMasonryDirective, NgxMasonryOptions } from 'ngx-masonry';
import { interval } from 'rxjs';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css'],
})
export class RecipeDetailComponent implements OnInit {

@ViewChild(NgxMasonryComponent) masonry: NgxMasonryComponent;
 @Input() response:any;
 public masonryOptions: NgxMasonryOptions = {
  gutter: 0,
  fitWidth:false,


};


  constructor() { }
  ngOnInit(): void {
  }
     reloadItems(){
      interval(100).subscribe(() => {
        this.masonry.layout();

    });

     }
}
