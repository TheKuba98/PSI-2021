import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { ArenaDto } from '../dto/arenaDto';
import { ArenaForm} from '../dto/arenaForm';
import { Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArenaService {

  constructor(
    private httpClient: HttpClient
  ) { }

  getAllArenas():Observable<ArenaDto[]>{
    return this.httpClient.get<ArenaDto[]>('http://localhost:8080/api/arenas');
  }

  getMyArenas():Observable<ArenaDto[]>{
    return this.httpClient.get<ArenaDto[]>('http://localhost:8080/api/my-arenas');
  }

  addArena(arena:ArenaForm):Observable<ArenaForm>{
    return this.httpClient.post<ArenaForm>('http://localhost:8080/api/my-arenas', arena);
  }
}
