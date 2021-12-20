import { NewCharacter } from './../models/new-character.model';
import { LevelUp } from './../models/level-up.model';
import { CharacterDetails } from './../models/character.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CharacterService {
  readonly baseUrl: string = "http://localhost:8000/api/v1/party-manager";

  constructor(
    private http: HttpClient
  ) { }


  public getParty(): Observable<CharacterDetails[]> {
    return this.http.get<CharacterDetails[]>(this.baseUrl + '/party');
  }

  public getGraveyard(): Observable<CharacterDetails[]> {
    return this.http.get<CharacterDetails[]>(this.baseUrl + '/graveyard');
  }

  public getCharacterById(id: number): Observable<CharacterDetails> {
    return this.http.get<CharacterDetails>(this.baseUrl + '/id/' + id);
  }

  public addCharacter(character: NewCharacter): Observable<CharacterDetails> {
    return this.http.post<CharacterDetails>(this.baseUrl + '/create', character);
  }

  public levelUpCharacter(levelUp: LevelUp): Observable<CharacterDetails> {
    return this.http.put<CharacterDetails>(this.baseUrl + '/level-up', levelUp);
  }

  public healCharacter(id: number, amount: number): Observable<CharacterDetails> {
    return this.http.put<CharacterDetails>(this.baseUrl + '/heal/' + id, null, { params: { healAmount: amount } });
  }

  public reviveCharacter(id: number): Observable<CharacterDetails> {
    return this.http.put<CharacterDetails>(this.baseUrl + '/revive/' + id, null);
  }

  public deleteCharacter(id: number): Observable<any> {
    return this.http.delete<any>(this.baseUrl + '/delete/' + id);
  }

  public requiredExperienceToLvlUp(level: number): number {
    let levelToNextLevel = 0;
    for (let index = 0; index <= level; index++) {
      levelToNextLevel = levelToNextLevel + index + 2;
    }
    return levelToNextLevel;
  }

}
