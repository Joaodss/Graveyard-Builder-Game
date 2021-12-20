import { SnackBarLogService } from './../../../core/services/snack-bar-log.service';
import { UserService } from './../../services/user.service';
import { LevelUp } from './../../models/level-up.model';
import { UserSharingService } from './../../services/user-sharing.service';
import { UserDetails } from './../../models/user.model';
import { CharacterSharingService } from './../../services/character-sharing.service';
import { CharacterService } from './../../services/character.service';
import { CharacterDetails } from './../../models/character.model';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-character-details',
  templateUrl: './character-details.component.html',
  styleUrls: ['./character-details.component.sass']
})
export class CharacterDetailsComponent implements OnInit {
  @Input() character!: CharacterDetails;
  @Input() experienceToLvlUp!: number;
  @Input() isAlive!: boolean;
  @Output() setSideNavTypeToAdd = new EventEmitter();
  @Output() closeSideNav = new EventEmitter();

  user!: UserDetails;
  currentPartySize!: number;


  levelingUp = false;
  healthPoints = 0;
  energyPoints = 0;
  attackPoints = 0;
  confirmDelete = false;
  confirmRevive = false;


  constructor(
    private characterService: CharacterService,
    private characterSharingService: CharacterSharingService,
    private userSharing: UserSharingService,
    private userService: UserService,
    private snackBar: SnackBarLogService
  ) { }

  ngOnInit(): void {
    this.updateUser();
    this.characterSharingService.sharedParty.subscribe(party => this.currentPartySize = party.length);
  }

  private updateUser(): void {
    this.userSharing.getUserDetails()
    this.userSharing.sharedUser.subscribe(user => this.user = user);
  }

  startLevelUp(): void {
    this.healthPoints = 0;
    this.energyPoints = 0;
    this.attackPoints = 0;
    this.levelingUp = true;
  }

  cancelLevelUp(): void {
    this.healthPoints = 0;
    this.energyPoints = 0;
    this.attackPoints = 0;
    this.levelingUp = false;
  }

  LevelUp(): void {
    const goldAfterLvlUp = this.user.gold - this.character.level - 5;
    if (goldAfterLvlUp < 0) return;
    const levelUp: LevelUp = {
      id: this.character.id,
      healthPoints: this.healthPoints,
      energyPoints: this.energyPoints,
      attackPoints: this.attackPoints
    }
    this.characterService.levelUpCharacter(levelUp).subscribe({
      next: (data: CharacterDetails) => {
        this.cancelLevelUp();
        this.characterSharingService.getCharacters();
        this.character = data
        this.experienceToLvlUp = this.characterService.requiredExperienceToLvlUp(this.character.level);
        this.snackBar.openSnackBar(data.name + ' leveled up!', 'Close');
        this.userService.updateGold(goldAfterLvlUp).subscribe({
          next: () => this.updateUser(),
          error: (err) => console.log(err)
        });
      },
      error: (err) => console.log(err)
    });

  }

  addStat(stat: string): void {
    if (this.healthPoints + this.energyPoints + this.attackPoints < 3) {
      switch (stat) {
        case 'health':
          this.healthPoints++;
          break;
        case 'energy':
          this.energyPoints++;
          break;
        case 'attack':
          this.attackPoints++;
          break;
      }
    }
  }
  subtractStat(stat: string): void {
    switch (stat) {
      case 'health':
        if (this.healthPoints > 0)
          this.healthPoints--
        break;
      case 'energy':
        if (this.energyPoints > 0)
          this.energyPoints--
        break;
      case 'attack':
        if (this.attackPoints > 0)
          this.attackPoints--
        break;
    }
  }

  healCharacter(): void {
    const goldAfterHeal = this.user.gold - 5;
    if (goldAfterHeal < 0) return;
    this.characterService.healCharacter(this.character.id, 20).subscribe({
      next: (data: CharacterDetails) => {
        this.character = data;
        this.characterSharingService.getCharacters();
        this.snackBar.openSnackBar(data.name + ' was healed 20hp!', 'Close');
        this.userService.updateGold(goldAfterHeal).subscribe({
          next: () => this.updateUser(),
          error: (err) => console.log(err)
        });
      },
      error: (err) => console.log(err)
    });
  }

  removeConfirmation(): void {
    this.confirmDelete = true;
  }

  removeCharacter(): void {
    const characterStatusAlive: boolean = this.character.isAlive;
    this.characterService.deleteCharacter(this.character.id).subscribe({
      next: () => {
        this.confirmDelete = false;
        this.snackBar.openSnackBar(this.character.name + ' was permanently removed!', 'Close');
        this.characterSharingService.getCharacters();
        if (characterStatusAlive) {
          this.setSideNavTypeToAdd.emit();
        } else {
          this.closeSideNav.emit();
        }
      },
      error: (err) => console.log(err)
    });
  }

  cancelRemove(): void {
    this.confirmDelete = false;
  }


  reviveConfirmation(): void {
    this.confirmRevive = true;
  }

  reviveCharacter(): void {
    const goldAfterHeal = this.user.gold - 5 - this.character.level;
    if (goldAfterHeal < 0) return;
    this.characterService.reviveCharacter(this.character.id).subscribe({
      next: () => {
        this.cancelRevive();
        this.characterSharingService.getCharacters();
        this.snackBar.openSnackBar(this.character.name + ' was revived!', 'Close');
        this.userService.updateGold(goldAfterHeal).subscribe({
          next: () => this.updateUser(),
          error: (err) => console.log(err)
        });
        this.closeSideNav.emit();
      },
      error: (err) => console.log(err)
    });
  }

  cancelRevive(): void {
    this.confirmRevive = false;
  }

}
