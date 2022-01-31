import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router, RouterEvent} from "@angular/router";
import {filter} from "rxjs/operators";
import {AuthService} from '../../auth/auth.service';
import {UserDto} from "../../user";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-navigationbar',
  templateUrl: './navigationbar.component.html',
  styleUrls: ['./navigationbar.component.scss']
})
export class NavigationbarComponent implements OnInit {

  actualUrl: string;
  username: string;
  firstName: string;
  lastName: string;
  roles: string;
  showStudentStuff: boolean = false;
  showEmployeeStuff: boolean = false;
  showRepresentativeStuff: boolean = false;


  @Input()
  userInfo: UserDto;

  @Output()
  logoutButtonClicked: EventEmitter<any> = new EventEmitter<any>();

  constructor(
    router: Router,
    private translate: TranslateService,
    private route: ActivatedRoute,
    private authService: AuthService) {
    router.events.pipe(
      filter((e) => e instanceof RouterEvent)
    )
      .subscribe((ev: RouterEvent) => this.actualUrl = ev.url)
  }

  ngOnInit(): void {
    this.authService.user.subscribe(response => {
      if (response !== null) {
        this.roles = response.roles;
        this.username = response.username;
        this.lastName = response.lastName;
        this.firstName = response.firstName;
      }
      else {
        this.roles = undefined;
        this.username = undefined;
        this.lastName = undefined;
        this.firstName = undefined;
      }
      this.showStudentStuff = this.roles  ? this.roles.includes('ROLE_STUDENT') : false;
      this.showEmployeeStuff = this.roles  ? this.roles.includes('ROLE_EMPLOYEE') : false;
      this.showRepresentativeStuff = this.roles  ? this.roles.includes('ROLE_REPRESENTATIVE') : false;
    });
  }

  logout() {
    this.logoutButtonClicked.emit()
  }

  setLanguage(lang: string) {
    this.translate.use(lang);
  }
}
