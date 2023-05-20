import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MyDashboardComponent } from './my-dashboard/my-dashboard.component';
import { ReservationComponent } from './reservation/reservation.component';


const routes: Routes = [
 // {
 //   path: '',
  //  component: MyDashboardComponent,
 // }
 
 { path: '', redirectTo: '/search', pathMatch: 'full' },
 { path: 'search', component: MyDashboardComponent },
 { path: 'bookings', component: ReservationComponent }
 
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})




export class AppRoutingModule { }
