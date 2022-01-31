import { Component, OnInit } from '@angular/core';
import { ArenaDto } from '../../model/dto/arenaDto';
import { ArenaService } from '../../services/arena.service';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-facilities',
  templateUrl: './facilities.component.html',
  styleUrls: ['./facilities.component.scss']
})
export class FacilitiesComponent implements OnInit {

  arenas: ArenaDto[];
  constructor(
    private arenaService:ArenaService,
    private authService:AuthService
  ) { }

  loading = false;
  roles:string;
  showAdminStuff:boolean=false;
  showOrganizerStuff:boolean=false;
  showUserStuff:boolean=false;
  displayedColumns=['arenaName','address','capacity','adminNames']

  fetchAllArenas(){
    this.loading=true;
    this.arenaService.getAllArenas().subscribe(response=>{this.arenas = response; this.loading=false;});
  }

  fetchMyArenas(){
    this.loading=true;
    this.arenaService.getMyArenas().subscribe(response=>{this.arenas = response; this.loading=false;});
  }

  tabChanged(tabChangeEvent: MatTabChangeEvent): void {
    console.log('tabChangeEvent => ', tabChangeEvent);
    console.log('index => ', tabChangeEvent.index);
    if(tabChangeEvent.index===0){
      this.fetchAllArenas();
    }
    else if(tabChangeEvent.index===1){
      this.fetchMyArenas();
    }
  }

  ngOnInit(): void {
    this.fetchAllArenas();
    this.authService.user.subscribe(response=>{
      this.roles = response.roles
    });
    this.showAdminStuff = this.roles.includes('ROLE_ADMIN');
    this.showUserStuff = this.roles.includes('ROLE_USER');
    this.showOrganizerStuff = this.roles.includes('ROLE_ORGANIZER');
  }

}
