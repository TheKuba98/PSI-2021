import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {LoginComponent} from "./components/login/login.component";
import { ThesisListComponent } from './components/thesis-list/thesis-list.component';
import { MyThesisListComponent } from './components/my-thesis-list/my-thesis-list.component';
import { ThesisFormComponent } from './components/thesis-form/thesis-form.component';
import { ThesisUpdateComponent } from './components/thesis-update/thesis-update.component';
import { ReviewersComponent } from './components/reviewers/reviewers.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component:LoginComponent},
  { path: 'theses', component:ThesisListComponent},
  { path: 'my-theses', component:MyThesisListComponent},
  { path: 'update-thesis/:id', component:ThesisUpdateComponent},
  { path: 'add-thesis', component:ThesisFormComponent},
  { path: 'reviewers', component:ReviewersComponent},

  //otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
