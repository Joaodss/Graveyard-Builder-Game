import { CharacterType } from './../../models/character-type.enum';
import { CharacterDetails } from './../../models/character.model';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-character-grid-item',
  templateUrl: './character-grid-item.component.html',
  styleUrls: ['./character-grid-item.component.sass']
})
export class CharacterGridItemComponent implements OnInit {
  @Input() character!: CharacterDetails;
  @Input() type!: string;
  @Input() experienceToLvlUp!: number;
  @Input() isSelected: boolean = false;
  @Input() isFighting: boolean = false;
  @Input() isDead: boolean = false;

  experiencePercentage!: any;

  iconMap = new Map<CharacterType, string>([
    [CharacterType.WARRIOR, './../../../../assets/images/character-types/sword.svg'],
    [CharacterType.ARCHER, './../../../../assets/images/character-types/bow.svg'],
    [CharacterType.MAGE, './../../../../assets/images/character-types/staff.svg']
  ]);

  graveMap = new Map<CharacterType, string>([
    [CharacterType.WARRIOR, './../../../../assets/images/graves/warrior-grave.svg'],
    [CharacterType.ARCHER, './../../../../assets/images/graves/archer-grave.svg'],
    [CharacterType.MAGE, './../../../../assets/images/graves/mage-grave.svg']
  ]);



  constructor() {
    this.calculateExperiencePercentage();
  }

  ngOnInit(): void {
    this.calculateExperiencePercentage();
  }

  calculateExperiencePercentage(): void {
    if (!this.experienceToLvlUp) return;
    const experiencePercentage = (this.character.experience / this.experienceToLvlUp) * 100
    this.experiencePercentage = { width: experiencePercentage + '%' };
  }

}

