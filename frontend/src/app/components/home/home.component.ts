import { Component, OnInit } from '@angular/core';

import {map} from "rxjs/operators";
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  user : string;
  username:string;
  roles:string;
  showStudentStuff:boolean=false;
  showEmployeeStuff:boolean=false;
  showRepresentativeStuff:boolean=false;

  constructor(private authService : AuthService) { }

  ngOnInit(): void {
    this.authService.user.subscribe(response=>{
      this.roles = response.roles
      this.username = response.username;
    });
    this.showStudentStuff = this.roles.includes('ROLE_STUDENT');
    this.showEmployeeStuff = this.roles.includes('ROLE_EMPLOYEE');
    this.showRepresentativeStuff = this.roles.includes('ROLE_REPRESENTATIVE');
  }

  getUserData() {
    this.authService.checkInterceptors().subscribe((u) => {this.user = u;})
  }

}
