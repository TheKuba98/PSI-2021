import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from 'src/app/auth/auth.service';
import { FilterOptionsDto } from 'src/app/model/dto/filterOptionsDto';
import { MessageDto } from 'src/app/model/dto/messageDto';
import { ThesisDto } from 'src/app/model/dto/thesisDto';
import { ThesisSearchDto } from 'src/app/model/dto/thesisSearchDto';
import { ThesisService } from 'src/app/services/thesis.service';

@Component({
  selector: 'app-reviewers',
  templateUrl: './reviewers.component.html',
  styleUrls: ['./reviewers.component.scss']
})
export class ReviewersComponent implements OnInit {

  loading:boolean=false;
  theses: ThesisDto[];
  filterOptions:FilterOptionsDto;
  reviwer:string="";
  thema:string="";
  supervisor:string="";
  type:string="";
  year:string="";
  field:string="";
  language:string="";
  reviewers: any = {};
  message:MessageDto;
  username: string;
  firstName: string;
  lastName: string;
  roles: string;
  showStudentStuff: boolean = false;
  showEmployeeStuff: boolean = false;
  showRepresentativeStuff: boolean = false;

  displayedColumns:string[] =  ['theme','authors','supervisor','type','year','field', 'language', 'reviewers','action'];

  constructor(
    private thesisService:ThesisService,
    private snackBar:MatSnackBar,
    private translateService:TranslateService,
    private authService: AuthService,
  ) { }

  reserveThesis(thesisId: number){
    console.log("Reserved!")
    this.thesisService.assignFirstStudentToThesis(thesisId).subscribe(response=>{
      console.log(response);
      this.loading=false;
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

    this.fetchAllAvailableFilteredThesesWithReviews(searchCriteria);
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action,{
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  clearFilters(){
    this.thema="";
    this.supervisor="";
    this.type="";
    this.year="";
    this.field="";
    this.language="";
  }

  addReviwer(thesisId:number, reveiwer:string){
    this.loading=true;
    this.thesisService.addReviewer(thesisId, reveiwer).subscribe(response=>{
      this.message = response;
      this.loading=false;
      console.log(response);
      this.fetchfilterOptions();
      this.filter();
      this.openSnackBar(this.translateService.instant('message.sent')+' '+response.receiver, this.translateService.instant('common.close'))
    },  (error) => {
      console.log(error);
      this.loading = false;
      this.openSnackBar(this.translateService.instant('error.'+error.error.key), this.translateService.instant('common.close'))
      this.fetchfilterOptions();
      this.filter();
    }

    );

 
  }

  fetchAllAvailableFilteredThesesWithReviews(thesisSearchDto:ThesisSearchDto){
    this.loading=true;
    this.thesisService.getThesisWithReviewers(thesisSearchDto).subscribe(response=>{this.theses = response;this.loading=false;});
  }

  fetchAllAvailableTheses(){
    this.loading=true;
    this.thesisService.getAllAvailableTheses().subscribe(response=>{this.theses = response;this.loading=false;console.log(response)});
  }

  fetchfilterOptions(){
    this.loading=true;
    this.thesisService.getFilterOptions().subscribe(response=>{this.filterOptions = response;console.log(response)});
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
    this.filter();
    this.fetchfilterOptions();
  }

}
