import { NgModule, APP_INITIALIZER, LOCALE_ID } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import { NavigationbarComponent } from './components/navigationbar/navigationbar.component';
import {MatMenuModule} from "@angular/material/menu";
import {MatButtonModule} from "@angular/material/button";
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {BasicAuthInterceptor} from "./interceptors/basic-auth.interceptor";
import { EventComponent } from './componOld/event/event.component';
import { FacilitiesComponent } from './componOld/facilities/facilities.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatTableModule} from '@angular/material/table';
import {MatTabsModule} from '@angular/material/tabs';
import {MatSelectModule} from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { FacilitiesFormComponent } from './componOld/facilities-form/facilities-form.component';
import { EventFormComponent } from './componOld/event-form/event-form.component';
import { EventDetailsComponent } from './componOld/event-details/event-details.component';
import { ThesisListComponent } from './components/thesis-list/thesis-list.component';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {MatRippleModule} from '@angular/material/core';
import { MyThesisListComponent } from './components/my-thesis-list/my-thesis-list.component';
import {MatExpansionModule} from '@angular/material/expansion';
import { ThesisFormComponent } from './components/thesis-form/thesis-form.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { ThesisUpdateComponent } from './components/thesis-update/thesis-update.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    NavigationbarComponent,
    LoginComponent,
    HomeComponent,
    EventComponent,
    FacilitiesComponent,
    FacilitiesFormComponent,
    EventFormComponent,
    EventDetailsComponent,
    ThesisListComponent,
    MyThesisListComponent,
    ThesisFormComponent,
    ThesisUpdateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatProgressSpinnerModule,
    MatGridListModule,
    MatTabsModule,
    MatSelectModule,
    MatRippleModule,
    MatExpansionModule,
    MatSnackBarModule,
    
    FormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
      defaultLanguage: 'pl'
    })
    // I18NextModule.forRoot()
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true, },
    //  I18N_PROVIDERS
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
