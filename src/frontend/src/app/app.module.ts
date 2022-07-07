import { HttpClientModule } from '@angular/common/http';
import {  NgModule } from '@angular/core';
import {BrowserModule } from '@angular/platform-browser';
import { AccordionModule } from 'ngx-bootstrap/accordion'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './common/navbar/navbar.component';
import { HeaderComponent } from './common/header/header.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { SearchBarComponent } from './common/search-bar/search-bar.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { MatCommonModule } from '@angular/material/core';
import {MatIconModule} from '@angular/material/icon';
import { RecipeDetailComponent } from './ricette/recipe-detail/recipe-detail.component';
import { NgxMasonryModule } from 'ngx-masonry';
@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HeaderComponent,
    SearchBarComponent,
    RecipeDetailComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NoopAnimationsModule,
    MatFormFieldModule,
    MatChipsModule,
    MatCommonModule,
    MatCommonModule,
    MatIconModule,
    NgxMasonryModule,
    AccordionModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
