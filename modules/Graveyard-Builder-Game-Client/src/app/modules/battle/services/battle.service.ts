import { OpponentDetails } from './../../../shared/models/opponent.model';
import { UserDetails } from './../../../shared/models/user.model';
import { CharacterDetails } from '../../../shared/models/character.model';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BattleService {
  readonly baseUrl: string = "http://localhost:8000/api/v1/battle";


  constructor(
    private http: HttpClient
  ) { }


  getOpponent(): Observable<OpponentDetails> {
    return this.http.get<OpponentDetails>(this.baseUrl + '/opponents');
  }

  updateCharacterHealth(id: number, currentHealth: number): Observable<CharacterDetails> {
    return this.http.put<CharacterDetails>(this.baseUrl + '/updateHealth/' + id, null, { params: { health: currentHealth } });
  }

  addCharacterExperience(id: number, experienceGained: number): Observable<CharacterDetails> {
    return this.http.put<CharacterDetails>(this.baseUrl + '/addExperience/' + id, null, { params: { experience: experienceGained } });
  }

  addUserGoldAndExperience(goldGained: number, experienceGained: number): Observable<UserDetails> {
    return this.http.put<UserDetails>(this.baseUrl + '/addUserExperienceAndGold', null, { params: { gold: goldGained, experience: experienceGained } });
  }

}
