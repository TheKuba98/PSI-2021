import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from 'src/app/auth/auth.service';
import { FilterOptionsDto } from 'src/app/model/dto/filterOptionsDto';
import { ThesisDto } from 'src/app/model/dto/thesisDto';
import { ThesisSearchDto } from 'src/app/model/dto/thesisSearchDto';
import { ThesisService } from 'src/app/services/thesis.service';


@Component({
  selector: 'app-thesis-list',
  templateUrl: './thesis-list.component.html',
  styleUrls: ['./thesis-list.component.scss']
})
export class ThesisListComponent implements OnInit {

  loading:boolean=false;
  loadingFilters:boolean=false;
  theses: ThesisDto[];
  filterOptions:FilterOptionsDto;

  thema:string="";
  supervisor:string="";
  type:string="";
  year:string="";
  field:string="";
  language:string="";
  username: string;
  firstName: string;
  lastName: string;
  roles: string;
  showStudentStuff: boolean = false;
  showEmployeeStuff: boolean = false;
  showRepresentativeStuff: boolean = false;

  displayedColumns:string[] =  ['theme','supervisor','type','year','field', 'language', 'status'];

  constructor(
    private thesisService:ThesisService,
    private snackBar:MatSnackBar,
    private translateService:TranslateService,
    private authService: AuthService
  ) { }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action,{
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  reserveThesis(thesisId: number){
    console.log("Reserved!")
    this.thesisService.assignFirstStudentToThesis(thesisId).subscribe(response=>{
      console.log(response);
      this.loading=false;
      this.openSnackBar(this.translateService.instant('message.thesisReserved'), this.translateService.instant('common.close'))
      this.fetchAllAvailableTheses();
    },(error) => {
      console.log(error);
      this.loading = false;
      this.openSnackBar(this.translateService.instant('error.'+error.error.key), this.translateService.instant('common.close'))
      this.fetchAllAvailableTheses();
    });
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

    this.fetchAllAvailableFilteredTheses(searchCriteria);
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
    this.loadingFilters=true;
    this.thesisService.getFilterOptions().subscribe(response=>{this.filterOptions = response;this.loadingFilters=false;console.log(response)});
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
    this.fetchAllAvailableTheses();
    this.fetchfilterOptions();
  }

}
