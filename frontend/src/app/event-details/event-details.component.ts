import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { EventDto } from '../dto/eventDto';
import { EventService } from '../service/event.service';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.scss']
})
export class EventDetailsComponent implements OnInit {

  loading:boolean = false;
  username:string;
  isLoggedIn:boolean=false;
  showAdminStuff:boolean=false;
  showOrganizerStuff:boolean=false;
  showUserStuff:boolean=false;
  hasFilename:boolean=false;
  event:EventDto;
  roles:string;

  constructor(
    private eventService:EventService,
    private authService:AuthService,
    private route: ActivatedRoute,) { }

  fetchEventById(id:number){
    this.loading=true;
    this.eventService.getEventById(id).subscribe(response=>{
      this.event = response;
      this.hasFilename=!(response.filename==="")
      this.loading=false;
      console.log("eve", response)});
  }

  downloadFile(){
    console.log("id ev", this.event.eventId);
    this.eventService.downloadEventInfoFile(this.event.eventId).subscribe(blob =>{ console.log(blob); saveAs(blob, "eventInfo.pdf");});
  }

  takePartInEvent(id:number){
    this.loading=true;
    this.eventService.takePartInEvent(id).subscribe(response=>{
      console.log(response);
      this.fetchEventById(id)
    });
  }

  quitEvent(id:number){
    this.loading=true;
    this.eventService.quitEvent(id).subscribe(response=> {
      console.log(response);
      this.fetchEventById(id)
    });
  }

  acceptEvent(id:number){
    this.loading=true;
    this.eventService.acceptEvent(id).subscribe(response=> {
      console.log(response);
      this.fetchEventById(id)
    });
  }

  ngOnInit(): void {
    this.authService.user.subscribe(response=>{
      this.roles = response.roles
      this.username = response.username;
    });
    this.showAdminStuff = this.roles.includes('ROLE_ADMIN');
    this.showUserStuff = this.roles.includes('ROLE_USER');
    this.showOrganizerStuff = this.roles.includes('ROLE_ORGANIZER');
    const eventId = this.route.snapshot.paramMap.get('id');
    this.fetchEventById(Number(eventId));
  
  }

}
