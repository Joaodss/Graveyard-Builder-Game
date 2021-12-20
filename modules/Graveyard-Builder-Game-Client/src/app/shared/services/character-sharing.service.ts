import { CharacterService } from './character.service';
import { CharacterDetails } from './../models/character.model';
import { BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';
import { waitForAsync } from '@angular/core/testing';

@Injectable({
  providedIn: 'root'
})
export class CharacterSharingService {
  public sharedParty: BehaviorSubject<CharacterDetails[]> = new BehaviorSubject<CharacterDetails[]>([]);
  public sharedGraveyard: BehaviorSubject<CharacterDetails[]> = new BehaviorSubject<CharacterDetails[]>([]);

  constructor(private characterService: CharacterService) { }


  getCharacters() {
    this.getParty();
    this.getGraveyard();
  }

  resetCharacters() {
    this.sharedParty.next([]);
    this.sharedGraveyard.next([]);
  }

  setParty(party: CharacterDetails[]) {
    this.sharedParty.next(party);
  }

  setGraveyard(graveyard: CharacterDetails[]) {
    this.sharedGraveyard.next(graveyard);
  }

  private getParty() {
    this.characterService.getParty().subscribe({
      next: (party: CharacterDetails[]) => this.sharedParty.next(party),
      error: (err) => console.log(err)
    });
  }

  private getGraveyard() {
    this.characterService.getGraveyard().subscribe({
      next: (graveyard: CharacterDetails[]) => this.sharedGraveyard.next(graveyard),
      error: (err) => console.log(err)
    });
  }

}
