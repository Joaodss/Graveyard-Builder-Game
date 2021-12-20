import { SnackBarLogService } from './../../../../core/services/snack-bar-log.service';
import { RandomGenerationService, UserNameData } from './../../services/random-generation-service.service';
import { LevelUp } from './../../../../shared/models/level-up.model';
import { UserSharingService } from './../../../../shared/services/user-sharing.service';
import { NewCharacter } from './../../../../shared/models/new-character.model';
import { CharacterDetails } from './../../../../shared/models/character.model';
import { CharacterSharingService } from './../../../../shared/services/character-sharing.service';
import { CharacterService } from './../../../../shared/services/character.service';
import { Validators, FormControl, FormGroup, FormGroupDirective } from '@angular/forms';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-character-creator',
  templateUrl: './character-creator.component.html',
  styleUrls: ['./character-creator.component.sass']
})
export class CharacterCreatorComponent implements OnInit {
  characterPicMap = new Map<string, string>([
    ['WARRIOR', './../../../../../assets/images/character-default/warrior-picture.svg'],
    ['ARCHER', './../../../../../assets/images/character-default/archer-picture.svg'],
    ['MAGE', './../../../../../assets/images/character-default/mage-picture.svg']
  ]);

  @Output() setCreatedCharacter = new EventEmitter<CharacterDetails>();
  @Output() setSideNavTypeToDetails = new EventEmitter();

  createForm: FormGroup;
  name: FormControl;
  type: FormControl;
  imageURL: FormControl;
  healthPoints: FormControl;
  energyPoints: FormControl;
  attackPoints: FormControl;

  username!: string;

  constructor(
    private userSharing: UserSharingService,
    private characterService: CharacterService,
    private characterSharing: CharacterSharingService,
    private randomGeneration: RandomGenerationService,
    private snackBar: SnackBarLogService
  ) {
    this.name = new FormControl('', [Validators.required]);
    this.type = new FormControl('WARRIOR', [Validators.required]);
    this.imageURL = new FormControl('');
    this.healthPoints = new FormControl(0);
    this.energyPoints = new FormControl(0);
    this.attackPoints = new FormControl(0);
    this.createForm = new FormGroup({
      name: this.name,
      type: this.type,
      imageURL: this.imageURL,
      healthPoints: this.healthPoints,
      energyPoints: this.energyPoints,
      attackPoints: this.attackPoints
    });
  }

  ngOnInit(): void {
    this.userSharing.sharedUser.subscribe(user => this.username = user.username);
  }


  create(): void {
    const newCharacter: NewCharacter = {
      userUsername: this.username,
      type: this.type.value,
      name: this.name.value,
      pictureURL: (this.imageURL.value === '') ? this.characterPicMap.get(this.type.value) : this.imageURL.value,
    };

    this.characterService.addCharacter(newCharacter).subscribe({
      next: (character: CharacterDetails) => {
        const levelUp: LevelUp = {
          id: character.id,
          healthPoints: this.healthPoints.value,
          energyPoints: this.energyPoints.value,
          attackPoints: this.attackPoints.value
        };
        this.characterService.levelUpCharacter(levelUp).subscribe({
          next: (character: CharacterDetails) => {
            this.characterSharing.getCharacters();
            this.setCreatedCharacter.emit(character);
            this.setSideNavTypeToDetails.emit();
            this.name.setValue('');
            this.healthPoints.setValue(0);
            this.energyPoints.setValue(0);
            this.attackPoints.setValue(0);
            this.imageURL.setValue('');
            this.snackBar.openSnackBar(character.name + ' was created successfully!', 'Close');
          },
          error: err => console.log(err)
        });
      },
      error: err => console.log(err)
    });
  }

  addStat(stat: string): void {
    if (this.healthPoints.value + this.energyPoints.value + this.attackPoints.value < 3) {
      switch (stat) {
        case 'health':
          this.healthPoints.setValue(Number(this.healthPoints.value) + 1);
          break;
        case 'energy':
          this.energyPoints.setValue(Number(this.energyPoints.value) + 1);
          break;
        case 'attack':
          this.attackPoints.setValue(Number(this.attackPoints.value) + 1);
          break;
      }
    }
  }

  subtractStat(stat: string): void {
    switch (stat) {
      case 'health':
        if (this.healthPoints.value > 0)
          this.healthPoints.setValue(Number(this.healthPoints.value) - 1);
        break;
      case 'energy':
        if (this.energyPoints.value > 0)
          this.energyPoints.setValue(Number(this.energyPoints.value) - 1);
        break;
      case 'attack':
        if (this.attackPoints.value > 0)
          this.attackPoints.setValue(Number(this.attackPoints.value) - 1);
        break;
    }
  }

  generateRandom(): void {
    const generated = this.randomGeneration.getRandomData();
    this.name.setValue(generated.name);
    this.type.setValue(generated.type);
    this.healthPoints.setValue(generated.healthPoints);
    this.energyPoints.setValue(generated.energyPoints);
    this.attackPoints.setValue(generated.attackPoints);
  }

}
