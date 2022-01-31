import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router, RouterEvent} from "@angular/router";
import {filter} from "rxjs/operators";
import { AuthService } from '../../auth/auth.service';
import {UserDto} from "../../user";

@Component({
  selector: 'app-navigationbar',
  templateUrl: './navigationbar.component.html',
  styleUrls: ['./navigationbar.component.scss']
})
export class NavigationbarComponent implements OnInit {

  actualUrl : string;
  username:string;
  firstName:string;
  lastName:string;
  roles:string;
  showStudentStuff:boolean=false;
  showEmployeeStuff:boolean=false;
  showRepresentativeStuff:boolean=false;


  @Input()
  userInfo : UserDto;

  @Output()
  logoutButtonClicked : EventEmitter<any> = new EventEmitter<any>();

  constructor(
    router : Router,
    private route: ActivatedRoute,
    private authService:AuthService) {
    router.events.pipe(
      filter((e) => e instanceof RouterEvent)
    )
      .subscribe((ev : RouterEvent) => this.actualUrl = ev.url)
  }

  ngOnInit(): void {
    this.authService.user.subscribe(response=>{
      this.roles = response.roles;
      this.username = response.username;
      this.lastName = response.lastName;
      this.firstName = response.firstName;
    });
    this.showStudentStuff = this.roles.includes('ROLE_STUDENT');
    this.showEmployeeStuff = this.roles.includes('ROLE_EMPLOYEE');
    this.showRepresentativeStuff = this.roles.includes('ROLE_REPRESENTATIVE');
  }

  logout() {
  this.logoutButtonClicked.emit()
  }

}
