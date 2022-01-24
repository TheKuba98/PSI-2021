import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent} from "@angular/common/http";
import { EventDto } from '../dto/eventDto';
import { Observable} from 'rxjs';
import { EventForm } from '../dto/eventForm';
import { ParticipationDto } from '../dto/participationDto';
import {EventDescription} from "../dto/eventDescription";
import { EventInfoFilename } from '../dto/eventInfoFilename';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(
    private httpClient: HttpClient) { }

  getAllEvents():Observable<EventDto[]>{
    return this.httpClient.get<EventDto[]>('http://localhost:8080/api/events');
  }

  getEventById(id:number):Observable<EventDto>{
    return this.httpClient.get<EventDto>('http://localhost:8080/api/events/'+id);
  }

  getMyEvents():Observable<EventDto[]>{
    return this.httpClient.get<EventDto[]>('http://localhost:8080/api/my-events');
  }

  getMyOrganizedEvents():Observable<EventDto[]>{
    return this.httpClient.get<EventDto[]>('http://localhost:8080/api/my-organized-events');
  }

  getEventsOnMyArenas():Observable<EventDto[]>{
    return this.httpClient.get<EventDto[]>('http://localhost:8080/api/my-arenas-events');
  }

  getEventsByArenaId(id: number):Observable<EventDto[]>{
    return this.httpClient.get<EventDto[]>('http://localhost:8080/api/arenas/'+id+'/events');
  }

  addEvent(event:EventForm):Observable<EventDto>{
    return this.httpClient.post<EventDto>('http://localhost:8080/api/my-events', event);
  }

  uploadEventInfoFile(file : File, eventId:number) : Observable<EventInfoFilename> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.httpClient.post<EventInfoFilename>('http://localhost:8080/api/events/'+eventId +'/file/upload', formData);
  }

  downloadEventInfoFile(eventId:number) : Observable<Blob> {
    return this.httpClient.get('http://localhost:8080/api/events/'+eventId+'/file/download', {
      responseType: 'blob'
    });
  }

  takePartInEvent(id:number):Observable<ParticipationDto>{
    return this.httpClient.post<ParticipationDto>('http://localhost:8080/api/events/'+id+'/take-part', {});
  }

  quitEvent(id:number):Observable<ParticipationDto>{
    return this.httpClient.post<ParticipationDto>('http://localhost:8080/api/events/'+id+'/quit', {});
  }

  acceptEvent(id:number):Observable<EventDto>{
    return this.httpClient.post<EventDto>('http://localhost:8080/api/events/'+id+'/accept', {});
  }

  getDescriptionFromUploadedImage(file : File) : Observable<EventDescription> {
    const formData: FormData = new FormData();
    formData.append('image', file);
    return this.httpClient.post<EventDescription>('http://localhost:8080/api/image/getdescription', formData);
  }
}
