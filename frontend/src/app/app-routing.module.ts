import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {FacilitiesComponent} from "./facilities/facilities.component";
import {EventComponent} from "./event/event.component";
import { FacilitiesFormComponent } from './facilities-form/facilities-form.component';
import { EventFormComponent } from './event-form/event-form.component';
import { EventDetailsComponent } from './event-details/event-details.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component:LoginComponent},
  { path: 'facilities', component:FacilitiesComponent},
  { path: 'event', component:EventComponent},
  { path: 'facilities-form', component:FacilitiesFormComponent},
  { path: 'event-form', component:EventFormComponent},
  { path: 'event-details/:id', component:EventDetailsComponent},

  //otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
