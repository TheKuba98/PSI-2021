import { Component, OnInit } from '@angular/core';
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

  displayedColumns:string[] =  ['theme','authors','supervisor','type','year','field', 'language', 'reviewers','action'];

  constructor(
    private thesisService:ThesisService
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
    });

 
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
    this.filter();
    this.fetchfilterOptions();
  }

}
