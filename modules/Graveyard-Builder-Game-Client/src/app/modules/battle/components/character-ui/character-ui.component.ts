import { CharacterType } from './../../../../shared/models/character-type.enum';
import { CharacterDetails } from './../../../../shared/models/character.model';
import { Component, Input, OnInit } from '@angular/core';
import { animate, sequence, stagger, state, style, transition, trigger, keyframes } from '@angular/animations';

@Component({
  selector: 'app-character-ui',
  templateUrl: './character-ui.component.html',
  styleUrls: ['./character-ui.component.sass'],
  animations: [
    trigger('displayDamage', [
      transition('show <=> again', [
        style({
          opacity: 0
        }),
        sequence([
          animate("2s", keyframes([
            style({ opacity: 1, offset: 0.02 }),
            style({ opacity: 1, transform: 'translateY(-4px) scale(1.2)', offset: 0.6 }),
            style({ opacity: 0, transform: 'translateY(-10px) scale(1.5)', offset: 1.0 })
          ]))
        ])
      ])
    ]),
    trigger('displayEnergy', [
      transition('show <=> again', [
        style({
          opacity: 0
        }),
        sequence([
          animate("3s", keyframes([
            style({ opacity: 1, offset: 0.02 }),
            style({ opacity: 1, offset: 0.7 }),
            style({ opacity: 0, offset: 1.0 })
          ]))
        ])
      ])
    ]),
    trigger('displayDescription', [
      transition('show <=> again', [
        style({
          opacity: 0
        }),
        sequence([
          animate("6s", keyframes([
            style({ opacity: 1, offset: 0.02 }),
            style({ opacity: 1, offset: 0.8 }),
            style({ opacity: 0, offset: 1.0 })
          ]))
        ])
      ])
    ])
  ]
})
export class CharacterUiComponent implements OnInit {
  iconMap = new Map<CharacterType, string>([
    [CharacterType.WARRIOR, './../../../../../assets/images/character-types/sword.svg'],
    [CharacterType.ARCHER, './../../../../../assets/images/character-types/bow.svg'],
    [CharacterType.MAGE, './../../../../../assets/images/character-types/staff.svg']
  ]);

  @Input() character!: CharacterDetails;

  @Input() action!: string;
  @Input() damageDone: number = 0;
  @Input() gaveCriticalStrike: boolean = false;

  @Input() damageReceived: number = 0;
  @Input() energyChanges: number = 0;

  @Input() isBlocked: boolean = false;
  @Input() receivedCriticalStrike: boolean = false;
  @Input() isManaPassive: boolean = false;

  displayDamageState: string = 'show';
  displayEnergyState: string = 'show';
  displayDescription: string = 'show';

  constructor() { }

  ngOnInit(): void {
  }


  getEnergy(): string {
    switch (this.character.type) {
      case 'WARRIOR':
      case 'ARCHER':
        return 'Stamina: ' + this.character.currentStamina.toString();
      case 'MAGE':
        return 'Mana: ' + this.character.currentMana.toString();
    }
    return '';
  }

  healthPercentage(): number {
    return (this.character.currentHealth / this.character.maxHealth) * 100;
  }

  displayEnergyChanges(): string {
    if (this.energyChanges > 0) return '+' + this.energyChanges.toString();
    return this.energyChanges.toString();
  }

  displayDamageReceived(): string {
    if (this.damageReceived > 0) return '-' + this.damageReceived.toString();
    if (this.damageReceived < 0) return '+' + (-this.damageReceived).toString();
    return '-0';
  }

  showDamage(): void {
    this.displayDamageState === 'show' ? this.displayDamageState = 'again' : this.displayDamageState = 'show';
  }
  showEnergy(): void {
    this.displayEnergyState === 'show' ? this.displayEnergyState = 'again' : this.displayEnergyState = 'show';
  }
  showDescription(): void {
    this.displayDescription === 'show' ? this.displayDescription = 'again' : this.displayDescription = 'show';
  }


}
