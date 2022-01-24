import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router, RouterEvent} from "@angular/router";
import {filter} from "rxjs/operators";
import {UserDto} from "../user";

@Component({
  selector: 'app-navigationbar',
  templateUrl: './navigationbar.component.html',
  styleUrls: ['./navigationbar.component.scss']
})
export class NavigationbarComponent implements OnInit {

  actualUrl : string;

  @Input()
  userInfo : UserDto;

  @Output()
  logoutButtonClicked : EventEmitter<any> = new EventEmitter<any>();

  constructor(router : Router,  private route: ActivatedRoute) {
    router.events.pipe(
      filter((e) => e instanceof RouterEvent)
    )
      .subscribe((ev : RouterEvent) => this.actualUrl = ev.url)
  }

  ngOnInit(): void {
  }

  logout() {
  this.logoutButtonClicked.emit()
  }

}
