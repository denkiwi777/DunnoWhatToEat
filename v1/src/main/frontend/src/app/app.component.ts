import { Component, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { MainAppService } from './main-app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnDestroy {
  
  constructor(private appService: MainAppService) {}
  ngOnInit() {
    this.getAllUsers();
    }
  ngOnDestroy(): void {
    throw new Error('Method not implemented.');
  }

  title = 'angular-nodejs-example';

  userForm = new FormGroup({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    email: new FormControl('',   Validators.required)
  });

  recipe: any;
  userCount = 0;

  destroy$: Subject<boolean> = new Subject<boolean>();

  onSubmit() {
    this.appService.addURecipe(this.userForm.value, this.userCount + 1).pipe(takeUntil(this.destroy$)).subscribe(data => {
      console.log('message::::', data);
      this.userCount = this.userCount + 1;
      console.log(this.userCount);
      this.userForm.reset();
    });
  }

  getAllUsers() {
    this.appService.getRecipe().subscribe((recipe)=>{
		this.recipe = recipe;
       
  });
    };
  }

  function takeUntil(destroy$: Subject<boolean>): import("rxjs").OperatorFunction<Object, unknown> {
    throw new Error('Function not implemented.');
  }

}


