import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-thesis-list',
  templateUrl: './thesis-list.component.html',
  styleUrls: ['./thesis-list.component.scss']
})
export class ThesisListComponent implements OnInit {

  loading:boolean=false;
  // theses:Thesis

  displayedColumns:string[] =  ['thema','supervisor','type','year','field', 'language', 'status'];

  constructor() { }

  ngOnInit(): void {
  }

}
