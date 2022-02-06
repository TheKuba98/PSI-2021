import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
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
  loadingFilters:boolean=false;
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
  coauthor:string="";

  displayedColumns:string[];

  constructor(
    private thesisService:ThesisService,
    private authService:AuthService,
    private snackBar:MatSnackBar,
    private translateService:TranslateService
  ) { }

  acceptThesis(thesisId: number){
    this.loading=true;
    this.thesisService.markThesisAsAssigned(thesisId).subscribe(response=>{
      console.log(response);
      this.openSnackBar(this.translateService.instant('message.thesisAccepted'), this.translateService.instant('common.close'))
      this.getAllFilteredThesis();
    },
    (error) => {
      console.log(error);
      this.loading = false;
      this.openSnackBar(this.translateService.instant('error.'+error.error.key), this.translateService.instant('common.close'))
      this.getAllFilteredThesis();
    });

  }

  rejectThesis(thesisId: number){
    this.loading=true;
    this.thesisService.markThesisAsRegistered(thesisId).subscribe(response=>{
      console.log(response);
      this.openSnackBar(this.translateService.instant('message.thesisRejected'), this.translateService.instant('common.close'))
      this.getAllFilteredThesis();
    });
  }

  markAsCompleted(thesisId: number){
    this.loading=true;
    this.thesisService.markThesisAsCompleted(thesisId).subscribe(response=>{
      console.log(response);
      this.openSnackBar(this.translateService.instant('message.thesisCompleted'), this.translateService.instant('common.close'))
      this.getAllFilteredThesis();
    });

  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action,{
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  asignCoauthor(thesisId: number){
    console.log(this.coauthor);
    this.loading=true;
    this.thesisService.assignAnotherStudentToThesis(this.coauthor, thesisId).subscribe(
      (response)=>{
      console.log(response);
      this.getAllFilteredThesis();
    },
    (error) => {
      console.log(error);
      this.loading = false;
      this.openSnackBar(this.translateService.instant('error.'+error.error.key), this.translateService.instant('common.close'))
      this.getAllFilteredThesis();
    }
    );
  }

  filter(){
    this.getAllFilteredThesis();
  }

  clearFilters(){
    this.thema="";
    this.supervisor="";
    this.type="";
    this.year="";
    this.field="";
    this.language="";
  }

  getAllFilteredThesis(){
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

  fetchAllAvailableFilteredTheses(thesisSearchDto:ThesisSearchDto){
    this.loading=true;
    this.thesisService.getAllAvailableFilteredTheses(thesisSearchDto).subscribe(response=>{this.theses = response;this.loading=false;});
  }

  fetchAllAvailableTheses(){
    this.loading=true;
    this.thesisService.getAllAvailableTheses().subscribe(response=>{this.theses = response;this.loading=false;console.log(response)});
  }

  fetchfilterOptions(){
    this.loadingFilters=true;
    this.thesisService.getFilterOptions().subscribe(response=>{this.filterOptions = response;this.loading=false;this.loadingFilters=false;console.log(response)});
  }
  
  fetchAllMyFilteredTheses(thesisSearchDto:ThesisSearchDto){
    this.loading=true;
    this.thesisService.getMyFilteredTheses(thesisSearchDto).subscribe(response=>{this.theses = response;this.loading=false;console.log(response)});
  }

  ngOnInit(): void {
    this.fetchfilterOptions();
    this.getAllFilteredThesis();

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
    this.displayedColumns = this.showStudentStuff ? 
    ['theme','person', 'authors', 'type','year','field', 'language', 'status']
    :
    ['theme','person','type','year','field', 'language', 'status'];
  }

}
