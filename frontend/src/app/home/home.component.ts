import { Component, OnInit } from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  user : string;

  constructor(private authService : AuthService) { }

  ngOnInit(): void {
  }

  getUserData() {
    this.authService.checkInterceptors().subscribe((u) => {this.user = u;})
  }

}
