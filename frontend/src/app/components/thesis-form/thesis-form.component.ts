import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/auth/auth.service';
import { FilterOptionsDto } from 'src/app/model/dto/filterOptionsDto';
import { ThesisService } from 'src/app/services/thesis.service';

@Component({
  selector: 'app-thesis-form',
  templateUrl: './thesis-form.component.html',
  styleUrls: ['./thesis-form.component.scss']
})
export class ThesisFormComponent implements OnInit {
  
  submitted:boolean=false;
  loading:boolean=false;
  loadingFilters:boolean=false;
  filterOptions:FilterOptionsDto;
  username: string;
  firstName: string;
  lastName: string;
  roles: string;
  showStudentStuff: boolean = false;
  showEmployeeStuff: boolean = false;
  showRepresentativeStuff: boolean = false;

  thesisForm: FormGroup;
  get form() { return this.thesisForm.controls; }
  get theme() { return this.thesisForm.get('theme'); }
  get supervisor() { return this.thesisForm.get('supervisor'); }
  get type() { return this.thesisForm.get('type'); }
  get year() { return this.thesisForm.get('year'); }
  get field() { return this.thesisForm.get('field'); }
  get language() { return this.thesisForm.get('language'); }
  // get address() { return this.facilityForm.get('address'); }
  // get capacity() { return this.facilityForm.get('capacity'); }

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private thesisService:ThesisService
  ) { }

  onSubmit(): void {
    console.log("Wysyłamy");

    if (this.thesisForm.invalid) {
      console.log("Niedobry");
      return;
    }

    console.log("Jest OK");
  }


  fetchfilterOptions(){
    this.loadingFilters=true;
    this.thesisService.getFilterOptions().subscribe(response=>{this.filterOptions = response;this.loading=false;this.loadingFilters=false;console.log(response)});
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

    this.fetchfilterOptions();

    this.thesisForm = this.formBuilder.group({
      theme: ['',Validators.required],
      supervisor: [''],
      type: ['', Validators.required],
      year: ['', Validators.required],
      field: ['', Validators.required],
      language: ['', Validators.required],
    });
    if (this.showStudentStuff){
      this.thesisForm.get('supervisor').setValidators(Validators.required);
    }
    
    
  }

}
