import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ArenaDto } from '../../model/dto/arenaDto';
import { EventForm } from '../../model/dto/eventForm';
import { ArenaService } from '../../services/arena.service';
import { EventService } from '../../services/event.service';

@Component({
  selector: 'app-event-form',
  templateUrl: './event-form.component.html',
  styleUrls: ['./event-form.component.scss']
})
export class EventFormComponent implements OnInit {

  arenas:ArenaDto[];
  eventForm: FormGroup;
  selectedArena:number;
  loading = false;
  submitted = false;
  error = '';
  fileToUpload: File | null = null;
  eventInfoFile: File | null = null;
  createdEventId:number;
  constructor(
    private formBuilder: FormBuilder,
    private eventService:EventService,
    private arenaService:ArenaService
  ) { }

  fetchAllArenas(){
    this.arenaService.getAllArenas().subscribe(response=>this.arenas = response);
  }

  ngOnInit(): void {
    this.fetchAllArenas();
    this.eventForm = this.formBuilder.group({
      eventName: ['', Validators.required],
      arenaId: ['', Validators.required],
      beginTimestamp: ['', Validators.required],
      endTimestamp: ['', Validators.required],
      ticketPrice: ['', Validators.compose([Validators.min(0), Validators.required])],
      eventDescription : ['', Validators.required],
      infoFileName: ['']
    });
  }

  get form() { return this.eventForm.controls; }
  get eventName() { return this.eventForm.get('eventName'); }
  get arenaId() { return this.eventForm.get('arenaId'); }
  get beginTimestamp() { return this.eventForm.get('beginTimestamp'); }
  get endTimestamp() { return this.eventForm.get('endTimestamp'); }
  get ticketPrice() { return this.eventForm.get('ticketPrice'); }
  get description() {return this.eventForm.get('eventDescription')}

  onSubmit(): void {
   /* if (this.eventForm.invalid) {
      return;
    }*/

    this.loading = true;
    const eventForm:EventForm = {
      eventName:this.eventForm.value.eventName,
      arenaId:this.selectedArena,
      beginTimestamp:this.eventForm.value.beginTimestamp,
      endTimestamp:this.eventForm.value.endTimestamp,
      ticketPrice:this.eventForm.value.ticketPrice,
      description:this.eventForm.value.eventDescription
    }
    this.eventService.addEvent(eventForm)
    .subscribe(
      response => {
        console.log(response);
        this.createdEventId=response.eventId;
        this.eventService.uploadEventInfoFile(this.eventInfoFile, this.createdEventId).subscribe(response=>console.log(response.filename))

        this.submitted = true;
        this.loading = false;
      },
      error => {
        console.log(error);
      });
    this.loading=false;
  }

  getEventInfoFile(files: FileList) {
    this.eventInfoFile = files.item(0);
    console.log(this.eventInfoFile);
    console.log(this.eventInfoFile.name);
    this.eventForm.patchValue({
      infoFileName: this.eventInfoFile.name
    });
  }


  getDescriptionFromImage(files: FileList) {
    this.fileToUpload = files.item(0);
    console.log(this.fileToUpload);
    this.loading = true;
    this.eventService.getDescriptionFromUploadedImage(this.fileToUpload)
      .subscribe((x) => {
       this.eventForm.patchValue({
         eventDescription : x.description
       });
       this.loading=false;
      });
  }
}

