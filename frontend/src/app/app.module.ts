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
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
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
// import { I18NextModule, ITranslationService, I18NEXT_SERVICE } from 'angular-i18next';


// const i18nextOptions = {
//   whitelist: ['en', 'pl'],
//   fallbackLng: 'en',
//   debug: true,
//   returnEmptyString: false,
//   ns: [
//     'translation',
//     'validation',
//     'error'          
//   ],
// }

// export function appInit(i18next: ITranslationService) {
//   return () => i18next.init(i18nextOptions);
// }

// export function localeIdFactory(i18next: ITranslationService)  {
//   return i18next.language;
// }

// export const I18N_PROVIDERS = [
// {
//   provide: APP_INITIALIZER,
//   useFactory: appInit,
//   deps: [I18NEXT_SERVICE],
//   multi: true
// },
// {
//   provide: LOCALE_ID,
//   deps: [I18NEXT_SERVICE],
//   useFactory: localeIdFactory
// }];


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
    ThesisListComponent
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
    FormsModule,
    // I18NextModule.forRoot()
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true, },
    //  I18N_PROVIDERS
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
