import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { FilterOptionsDto } from 'src/app/model/dto/filterOptionsDto';
import { ThesisDto } from 'src/app/model/dto/thesisDto';
import { ThesisSearchDto } from 'src/app/model/dto/thesisSearchDto';
import { UserDto } from 'src/app/model/dto/user';
import { ThesisService } from 'src/app/services/thesis.service';

@Component({
  selector: 'app-my-thesis-list',
  templateUrl: './my-thesis-list.component.html',
  styleUrls: ['./my-thesis-list.component.scss']
})
export class MyThesisListComponent implements OnInit {

  loading:boolean=false;
  theses: ThesisDto[];
  filterOptions:FilterOptionsDto;
  username: string;
  firstName: string;
  lastName: string;
  roles: string;
  showStudentStuff: boolean = false;
  showEmployeeStuff: boolean = false;
  showRepresentativeStuff: boolean = false;

  thema:string="";
  supervisor:string="";
  type:string="";
  year:string="";
  field:string="";
  language:string="";

  displayedColumns:string[] =  ['theme','supervisor','type','year','field', 'language', 'status'];

  constructor(
    private thesisService:ThesisService,
    private authService:AuthService
  ) { }

  modifyThesis(thesisId: number){
    console.log("Modified!")
  }

  filter(){
    const searchCriteria={
      theme: this.thema,
      supervisor: this.supervisor,
      type: this.type,
      year: this.year,
      fieldName: this.field,
      language: this.language
    }

    this.fetchAllMyFilteredTheses(searchCriteria);
  }

  clearFilters(){
    this.thema="";
    this.supervisor="";
    this.type="";
    this.year="";
    this.field="";
    this.language="";
  }

  fetchAllAvailableFilteredTheses(thesisSearchDto:ThesisSearchDto){
    this.loading=true;
    this.thesisService.getAllAvailableFilteredTheses(thesisSearchDto).subscribe(response=>{this.theses = response;this.loading=false;});
  }

  fetchAllAvailableTheses(){
    this.loading=true;
    this.thesisService.getAllAvailableTheses().subscribe(response=>{this.theses = response;this.loading=false;console.log(response)});
  }

  fetchfilterOptions(){
    this.loading=true;
    this.thesisService.getFilterOptions().subscribe(response=>{this.filterOptions = response;this.loading=false;console.log(response)});
  }
  
  fetchAllMyFilteredTheses(thesisSearchDto:ThesisSearchDto){
    this.loading=true;
    this.thesisService.getMyFilteredTheses(thesisSearchDto).subscribe(response=>{this.theses = response;this.loading=false;console.log(response)});
  }

  ngOnInit(): void {
    const searchCriteria={
      theme: this.thema,
      supervisor: this.supervisor,
      type: this.type,
      year: this.year,
      fieldName: this.field,
      language: this.language
    }
    this.fetchfilterOptions();
    this.fetchAllMyFilteredTheses(searchCriteria);

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

}
