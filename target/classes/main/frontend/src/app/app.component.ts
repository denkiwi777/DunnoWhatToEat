import { Component, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MainAppService } from './main-app.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnDestroy {

  constructor(private appService: MainAppService) {}

  title = 'angular-nodejs-example';

  userForm = new FormGroup({
    firstName: new FormControl('',Validators.required),
    lastName: new FormControl('',  Validators.required),
    email: new FormControl('',  Validators.required)
  });

  users: any[] = [];
  recipeCount = 0;

  destroy$: Subject<boolean> = new Subject<boolean>();

  onSubmit() {
    this.appService.addURecipe(this.userForm.value, this.recipeCount + 1).pipe(takeUntil(this.destroy$)).subscribe(data => {
      console.log('message::::', data);
      this.recipeCount = this.recipeCount + 1;
      console.log(this.recipeCount);
      this.userForm.reset();
    });
  }

  getAllUsers() {
    this.appService.getRecipe().pipe(takeUntil(this.destroy$)).subscribe((users: any) => {
		this.recipeCount = users.length;
        this.users = users;
    });
  }

  ngOnDestroy() {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }

  ngOnInit() {
	this. ();
  }
}