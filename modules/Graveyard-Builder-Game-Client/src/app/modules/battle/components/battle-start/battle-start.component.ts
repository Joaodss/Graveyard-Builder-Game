import { Router } from '@angular/router';
import { CharacterService } from './../../../../shared/services/character.service';
import { BattleService } from '../../services/battle.service';
import { CharacterSharingService } from './../../../../shared/services/character-sharing.service';
import { UserSharingService } from './../../../../shared/services/user-sharing.service';
import { CharacterDetails } from './../../../../shared/models/character.model';
import { UserDetails } from './../../../../shared/models/user.model';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-battle-start',
  templateUrl: './battle-start.component.html',
  styleUrls: ['./battle-start.component.sass']
})
export class BattleStartComponent implements OnInit {
  @ViewChild('widgetsContent') widgetsContent!: ElementRef;
  currentUserUsername = '';
  currentUserPicture = '';
  opponentUsername = '';
  opponentPicture = '';
  currentUser!: UserDetails;
  currentParty!: CharacterDetails[];
  selectedParty: CharacterDetails[] = [];
  selectedOpponents: CharacterDetails[] = [];

  inBattle: boolean = false;

  finishedBattle: boolean = false;
  battleWon: number = 0;
  experienceEarned: number = 0;
  goldEarned: number = 0;


  constructor(
    private userSharing: UserSharingService,
    private characterSharing: CharacterSharingService,
    private characterService: CharacterService,
    private battleService: BattleService,
    private route: Router
  ) { }


  ngOnInit(): void {
    this.getUserDetails();
    this.getPartyDetails();
    this.getOpponent();
  }


  getUserDetails() {
    this.userSharing.getUserDetails()
    this.userSharing.sharedUser.subscribe(
      user => {
        this.currentUser = user;
        this.currentUserUsername = user.username;
        this.currentUserPicture = user.profilePictureUrl;
      }
    );
  }
  getPartyDetails() {
    this.characterSharing.getCharacters();
    this.characterSharing.sharedParty.subscribe(party => this.currentParty = party);
  }
  getOpponent() {
    this.battleService.getOpponent().subscribe(
      opponent => {
        this.opponentUsername = opponent.username;
        this.opponentPicture = opponent.profilePictureUrl;
        this.selectedOpponents = opponent.opponents;
        this.restoreOpponentsHealth();
      }
    );
  }
  restoreOpponentsHealth(): void {
    this.selectedOpponents.forEach(opponent => {
      opponent.currentHealth = opponent.maxHealth;
    });
  }

  requiredExperienceToLvlUp(character: CharacterDetails): number {
    return this.characterService.requiredExperienceToLvlUp(character.level);
  }

  selectCharacter(character: CharacterDetails): void {
    if (this.selectedParty.includes(character)) {
      this.selectedParty = this.selectedParty.filter(c => c !== character);
    } else if (this.selectedParty.length < 5) {
      this.selectedParty.push(character);
    }
  }

  scrollLeft() {
    this.widgetsContent.nativeElement.scrollLeft -= 190;
  }

  scrollRight() {
    this.widgetsContent.nativeElement.scrollLeft += 190;
  }

  goToBattle(): void {
    this.inBattle = true;
  }

  setFinishedBAttle(battleStatus: any): void {
    this.inBattle = false;
    this.finishedBattle = true;
    this.battleWon = battleStatus.isWon;
    this.goldEarned = battleStatus.goldEarned;
    this.experienceEarned = battleStatus.experienceEarned;
  }

  goToParty(): void {
    this.inBattle = false;
    this.finishedBattle = false;
    this.route.navigate(['/party']);
  }

}
