import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FilterOptionsDto } from '../model/dto/filterOptionsDto';
import { ThesisDto } from '../model/dto/thesisDto';
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
}
