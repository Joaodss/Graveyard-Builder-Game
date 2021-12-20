import { UserService } from './user.service';
import { UserDetails } from './../models/user.model';
import { BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserSharingService {
  public sharedUser: BehaviorSubject<UserDetails> = new BehaviorSubject<UserDetails>({
    username: '',
    email: '',
    profilePictureUrl: '',
    experience: 0,
    gold: 0,
    partyLevel: 0
  });

  constructor(private userService: UserService) { }

  getUserDetails(): void {
    this.userService.getUserDetails().subscribe({
      next: (user: UserDetails) => this.sharedUser.next(user),
      error: (err) => console.log(err)
    });
  }

  resetUserDetails(): void {
    this.sharedUser.next({
      username: '',
      email: '',
      profilePictureUrl: '',
      experience: 0,
      gold: 0,
      partyLevel: 0
    });
  }

  setUser(user: UserDetails): void {
    this.sharedUser.next(user);
  }

}
