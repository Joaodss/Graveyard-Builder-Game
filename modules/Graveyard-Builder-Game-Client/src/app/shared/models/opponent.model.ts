import { CharacterDetails } from './character.model';

export interface OpponentDetails {
  username: string,
  profilePictureUrl: string,
  opponents: CharacterDetails[]
}
