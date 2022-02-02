import { Component, OnInit } from '@angular/core';
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

  displayedColumns:string[] =  ['theme','supervisor','type','year','field', 'language', 'status'];

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
    this.fetchAllAvailableTheses();
    this.fetchfilterOptions();
  }

}
