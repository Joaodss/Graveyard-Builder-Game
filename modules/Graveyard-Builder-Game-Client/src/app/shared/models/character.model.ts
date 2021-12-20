import { CharacterType } from './character-type.enum';

export interface CharacterDetails {
  id: number,
  userUsername: string,
  type: CharacterType,
  isAlive: boolean,
  deathTime: Date,
  name: string,
  pictureURL: string,
  level: number,
  experience: number,
  maxHealth: number,
  currentHealth: number,
  passiveChance: number,
  maxStamina: number,
  currentStamina: number,
  strength: number,
  accuracy: number,
  maxMana: number,
  currentMana: number,
  intelligence: number
}