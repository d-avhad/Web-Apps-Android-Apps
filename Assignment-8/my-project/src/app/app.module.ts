import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MyDashboardComponent } from './my-dashboard/my-dashboard.component';

import { HttpClientModule } from '@angular/common/http';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatTabsModule } from '@angular/material/tabs'; 

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from "@angular/material/card";
import { MatButtonModule } from "@angular/material/button";
import { GoogleMapsModule } from '@angular/google-maps' ;

import {MatDatepickerModule} from '@angular/material/datepicker';

//import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { DatePipe } from '@angular/common'

//import { SocialLoginModule, SocialAuthServiceConfig, FacebookLoginProvider } from 'angularx-social-login';
import { ReservationComponent } from './reservation/reservation.component';


import { MatAutocompleteModule } from '@angular/material/autocomplete';
//import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
//import { MatButtonModule } from '@angular/material/button';




@NgModule({
  declarations: [
    AppComponent,
	MyDashboardComponent,
 ReservationComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
	FormsModule,
	ReactiveFormsModule,
	HttpClientModule,
	MatTabsModule,
	BrowserAnimationsModule,
	MatCardModule, 
   MatButtonModule,
   GoogleMapsModule,
   //SocialLoginModule,
   MatDatepickerModule,
   MatInputModule,
   MatFormFieldModule,
   MatNativeDateModule,
    MatIconModule,
    MatAutocompleteModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
