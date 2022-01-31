import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {LoginComponent} from "./components/login/login.component";
import {FacilitiesComponent} from "./componOld/facilities/facilities.component";
import {EventComponent} from "./componOld/event/event.component";
import { FacilitiesFormComponent } from './componOld/facilities-form/facilities-form.component';
import { EventFormComponent } from './componOld/event-form/event-form.component';
import { EventDetailsComponent } from './componOld/event-details/event-details.component';
import { ThesisListComponent } from './components/thesis-list/thesis-list.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component:LoginComponent},
  { path: 'theses', component:ThesisListComponent},
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
