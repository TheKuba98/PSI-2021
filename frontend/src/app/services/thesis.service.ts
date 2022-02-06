import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FilterOptionsDto } from '../model/dto/filterOptionsDto';
import { MessageDto } from '../model/dto/messageDto';
import { ThesisDto } from '../model/dto/thesisDto';
import { ThesisFormDto } from '../model/dto/thesisFormDto';
import { ThesisSearchDto } from '../model/dto/thesisSearchDto';

@Injectable({
  providedIn: 'root'
})
export class ThesisService {

  constructor(
    private httpClient: HttpClient
    ) { }

    getAllAvailableFilteredTheses(thesisSearchDto:ThesisSearchDto):Observable<ThesisDto[]>{
      return this.httpClient.post<ThesisDto[]>('http://localhost:8080/api/theses',thesisSearchDto);
    }

    getAllAvailableTheses():Observable<ThesisDto[]>{
      return this.httpClient.get<ThesisDto[]>('http://localhost:8080/api/all-theses');
    }

    getFilterOptions():Observable<FilterOptionsDto>{
      return this.httpClient.get<FilterOptionsDto>('http://localhost:8080/api/filter-options');
    }
    
    getMyFilteredTheses(thesisSearchDto:ThesisSearchDto):Observable<ThesisDto[]> {
      return this.httpClient.post<ThesisDto[]>('http://localhost:8080/api/my-theses',thesisSearchDto);
    }

    assignFirstStudentToThesis(thesisId:number):Observable<ThesisDto> {
      return this.httpClient.post<ThesisDto>('http://localhost:8080/api/theses/'+ thesisId+'/assign', null);
    }

    assignAnotherStudentToThesis(username:string, thesisId:number):Observable<ThesisDto> {
      return this.httpClient.post<ThesisDto>('http://localhost:8080/api/theses/'+ thesisId+'/assign?username='+ username, null);
    }

    markThesisAsCompleted(thesisId:number):Observable<ThesisDto> {
      return this.httpClient.post<ThesisDto>('http://localhost:8080/api/theses/'+ thesisId+'/complete', null);
    }

    markThesisAsAssigned(thesisId:number):Observable<ThesisDto> {
      return this.httpClient.post<ThesisDto>('http://localhost:8080/api/theses/'+ thesisId+'/accept', null);
    }

    markThesisAsRegistered(thesisId:number):Observable<ThesisDto> {
      return this.httpClient.post<ThesisDto>('http://localhost:8080/api/theses/'+ thesisId+'/reject', null);
    }

    addThesis(thesis:ThesisFormDto):Observable<ThesisDto>{
      return this.httpClient.post<ThesisDto>('http://localhost:8080/api/add-thesis', thesis);
    }

    updateThesis(thesis:ThesisFormDto, id:number):Observable<ThesisDto>{
      return this.httpClient.put<ThesisDto>('http://localhost:8080/api/update-thesis?thesisId='+id, thesis);
    }

    getAvailableThesisById(id:number):Observable<ThesisDto>{
      return this.httpClient.get<ThesisDto>('http://localhost:8080/api/theses/'+id);
    }

    getThesisWithReviewers(thesisSearchDto:ThesisSearchDto):Observable<ThesisDto[]>{
      return this.httpClient.post<ThesisDto[]>('http://localhost:8080/api/theses-review',thesisSearchDto);
    }

    addReviewer(thesisId:number, username:string):Observable<MessageDto> {
      return this.httpClient.post<MessageDto>('http://localhost:8080/api/theses/'+thesisId+'/add-review?username='+ username, null);
    }
}
