import { Component, OnInit } from '@angular/core';
import { EventDto } from '../../model/dto/eventDto';
import { EventService } from '../../services/event.service';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { ArenaDto } from '../../model/dto/arenaDto';
import { ArenaService } from '../../services/arena.service';
import { AuthService } from '../../auth/auth.service';
import { UserDto } from '../../model/dto/user';


@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss']
})
export class EventComponent implements OnInit {

  loading:boolean = false;
  username:string;
  isLoggedIn:boolean=false;
  showAdminStuff:boolean=false;
  showOrganizerStuff:boolean=false;
  showUserStuff:boolean=false;
  events: EventDto[];
  arenas: ArenaDto[];
  roles:string;
  selectedArena: string;
  selectedTab:number=0;
  isTabMyOrganized:boolean=this.selectedTab===3;
  isTabAll:boolean=this.selectedTab===0;
  constructor(
    private eventService:EventService,
    private arenaService:ArenaService,
    private authService:AuthService
  ) { }

  displayedColumns:string[];

  fetchAllEvents(){
    this.loading=true;
    this.eventService.getAllEvents().subscribe(response=>{this.events = response;this.loading=false;});
  }

  fetchMyEvents(){
    this.loading=true;
    this.eventService.getMyEvents().subscribe(response=>{this.events = response;this.loading=false;});
  }

  fetchMyOrganizedEvents(){
    this.loading=true;
    this.eventService.getMyOrganizedEvents().subscribe(response=>{this.events = response;this.loading=false;});
  }

  fetchEventsOnMyArenas(){
    this.loading=true;
    this.eventService.getEventsOnMyArenas().subscribe(response=>{this.events = response;this.loading=false;});
  }

  fetchEventsByArenaId(){
    this.loading=true;
    this.eventService.getEventsByArenaId(Number(this.selectedArena)).subscribe(response=>{this.events = response;this.loading=false;});
  }

  fetchAllArenas(){
    this.loading=true;
    this.arenaService.getAllArenas().subscribe(response=>{this.arenas = response;this.loading=false;});
  }

  takePartInEvent(id:number){
    this.loading=true;
    this.eventService.takePartInEvent(id).subscribe(response=>{
      console.log(response);
      this.getEventsBasedOnTabs(this.selectedTab)
    });
  }

  quitEvent(id:number){
    this.loading=true;
    this.eventService.quitEvent(id).subscribe(response=> {
      console.log(response);
      this.getEventsBasedOnTabs(this.selectedTab)
    });
  }

  acceptEvent(id:number){
    this.loading=true;
    this.eventService.acceptEvent(id).subscribe(response=> {
      console.log(response);
      this.getEventsBasedOnTabs(this.selectedTab)
    });
  }

  tabChanged(tabChangeEvent: MatTabChangeEvent): void {
    console.log('tabChangeEvent => ', tabChangeEvent);
    console.log('index => ', tabChangeEvent.index);
    this.selectedTab=tabChangeEvent.index;
    this.getEventsBasedOnTabs(tabChangeEvent.index);
  }

  getEventsBasedOnTabs(tab:number){
    switch(tab){
      case 0:
        this.fetchAllEvents();
        break;
      case 1:
        this.events=[];
        this.fetchMyEvents();
        break;
      case 2:
        this.events=[];
        this.fetchEventsByArenaId();
        this.fetchAllArenas();
        break;
      case 3:
        if (this.showOrganizerStuff) {
          this.events = [];
          this.fetchMyOrganizedEvents();
        } else if (this.showAdminStuff) {
          this.events = [];
          this.fetchEventsOnMyArenas();
        }
        break;
      default:
        break;
    }
  }

  ngOnInit(): void {
    this.fetchAllEvents();
    this.authService.user.subscribe(response=>{
      this.roles = response.roles
      this.username = response.username;
    });
    this.showAdminStuff = this.roles.includes('ROLE_ADMIN');
    this.showUserStuff = this.roles.includes('ROLE_USER');
    this.showOrganizerStuff = this.roles.includes('ROLE_ORGANIZER');
    this.displayedColumns = this.showOrganizerStuff || this.showAdminStuff ?
                            ['eventName','beginTimestamp','ticketPrice','arenaName','address', 'participate', 'accepted', 'details']
                          : ['eventName','beginTimestamp','ticketPrice','arenaName','address', 'participate', 'details']
  }

  
}
