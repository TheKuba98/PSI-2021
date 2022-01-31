import { Component } from '@angular/core';
import {UserDto} from "./user";
import {Router} from "@angular/router";
import {AuthService} from "./auth/auth.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';

  user: UserDto;

  constructor(
    private router: Router,
    private authenticationService: AuthService,
    translate : TranslateService
  ) {
    this.authenticationService.user.subscribe(x => this.user = x);
    translate.setDefaultLang('pl');
  }

  logout() {
    this.authenticationService.logout();
  }
}
