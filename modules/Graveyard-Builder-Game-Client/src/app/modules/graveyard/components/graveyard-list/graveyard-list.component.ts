import { CharacterSharingService } from './../../../../shared/services/character-sharing.service';
import { CharacterService } from './../../../../shared/services/character.service';
import { CharacterDetails } from './../../../../shared/models/character.model';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';

@Component({
  selector: 'app-graveyard-list',
  templateUrl: './graveyard-list.component.html',
  styleUrls: ['./graveyard-list.component.sass']
})
export class GraveyardListComponent implements OnInit {
  @ViewChild('sidenav') public sidenav!: MatSidenav;
  graveyard!: CharacterDetails[];
  graveyardSize!: number;
  selectedCharacter!: CharacterDetails;
  selectedInfoType!: string;

  constructor(
    private characterService: CharacterService,
    private characterSharing: CharacterSharingService
  ) { }


  ngOnInit(): void {
    this.characterSharing.getCharacters();
    this.getGraveyardDetails();
  }


  defineInfoType(infoType: string): void {
    this.selectedInfoType = infoType;
  }

  selectCharacter(grave: any): void {
    this.selectedCharacter = grave;
  }

  requiredExperienceToLvlUp(character: CharacterDetails): number {
    return this.characterService.requiredExperienceToLvlUp(character.level);
  }

  private getGraveyardDetails(): void {
    this.characterSharing.sharedGraveyard.subscribe(
      (graveyard: CharacterDetails[]) => {
        this.graveyard = graveyard
        this.graveyardSize = graveyard.length;
      }
    );
  }
}
