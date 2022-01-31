import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { ArenaForm } from '../../model/dto/arenaForm';
import { ArenaService } from '../../services/arena.service';

@Component({
  selector: 'app-facilities-form',
  templateUrl: './facilities-form.component.html',
  styleUrls: ['./facilities-form.component.scss']
})
export class FacilitiesFormComponent implements OnInit {

  facilityForm: FormGroup;
  loading = false;
  submitted = false;
  error = '';
  constructor(
    private formBuilder: FormBuilder,
    private arenaService:ArenaService){ }

  ngOnInit(): void {
    this.facilityForm = this.formBuilder.group({
      arenaName: ['', Validators.required],
      address: ['', Validators.required],
      capacity: ['', Validators.compose([Validators.min(1), Validators.required])]
    });
  }

  get form() { return this.facilityForm.controls; }
  get arenaName() { return this.facilityForm.get('arenaName'); }
  get address() { return this.facilityForm.get('address'); }
  get capacity() { return this.facilityForm.get('capacity'); }

  onSubmit(): void {
    if (this.facilityForm.invalid) {
      return;
    }

    this.loading = true;
    const arenaForm:ArenaForm = {
      capacity:this.facilityForm.value.capacity,
      address:this.facilityForm.value.address,
      arenaName:this.facilityForm.value.arenaName
    }

    this.arenaService.addArena(arenaForm)
    .subscribe(
      response => {
        console.log(response);
        this.submitted = true;
        this.loading = false;
      },
      error => {
        console.log(error);
      });
  }

}
