import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from 'src/app/auth/auth.service';
import { FilterOptionsDto } from 'src/app/model/dto/filterOptionsDto';
import { ThesisDto } from 'src/app/model/dto/thesisDto';
import { ThesisFormDto } from 'src/app/model/dto/thesisFormDto';
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
  thesis:ThesisDto;

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
    private thesisService:ThesisService,
    private snackBar:MatSnackBar,
    private translateService:TranslateService,
    private activatedRoute: ActivatedRoute
  ) { }

  onSubmit(): void {
    if (this.thesisForm.invalid) {
      return;
    }
    this.createThesis();
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action,{
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }


  fetchfilterOptions(): void {
    this.loadingFilters=true;
    this.thesisService.getFilterOptions().subscribe(response=>{this.filterOptions = response;this.loading=false;this.loadingFilters=false;console.log(response)});
  }

  getAvailableThesisById(id:number): void {
    this.loading=true;
    this.thesisService.getAvailableThesisById(id).subscribe(response=>{this.thesis = response;this.loading=false;console.log(response)});
  }

  createThesis(): void {
    this.loading = true;
    const thesisForm:ThesisFormDto = {
      theme:this.theme.value,
      supervisor:this.supervisor.value,
      type:this.type.value,
      year:this.year.value,
      field:this.field.value,
      language:this.language.value
    }
    this.thesisService.addThesis(thesisForm)
    .subscribe(
      response => {
        console.log(response);
        this.submitted = true;
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
        this.openSnackBar(this.translateService.instant("error."+error.error.key), this.translateService.instant('common.close'))
      });
    this.loading=false;
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
