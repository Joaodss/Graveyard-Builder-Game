import { UserSharingService } from './../../../../shared/services/user-sharing.service';
import { Router } from '@angular/router';
import { AuthService } from './../../../../core/services/auth.service';
import { UserDetails } from './../../../../shared/models/user.model';
import { UserService } from './../../../../shared/services/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.sass']
})
export class ProfileDetailsComponent implements OnInit {
  isImageEditMode: boolean = false;
  updatedImageUrl: string = '';
  user: UserDetails = {
    username: '',
    email: '',
    profilePictureUrl: '',
    experience: 0,
    gold: 0,
    partyLevel: 0
  };

  constructor(
    private route: Router,
    private authService: AuthService,
    private userService: UserService,
    private userSharing: UserSharingService
  ) { }

  ngOnInit(): void {
    this.getUserDetails();
  }


  imageUpdate(): void {
    this.userService.updateImage(this.updatedImageUrl).subscribe({
      next: (user: UserDetails) => {
        this.user.username = user.username;
        this.user.email = user.email;
        this.user.profilePictureUrl = user.profilePictureUrl;
        this.closeImageForm();
      },
      error: (err: any) => console.log(err),
    });
  }

  openImageForm(): void {
    this.isImageEditMode = !this.isImageEditMode;
  }

  closeImageForm(): void {
    this.isImageEditMode = false;
  }

  logout(): void {
    this.authService.logout();
    this.userSharing.resetUserDetails();
    this.route.navigate(['/login']);
  }


  private getUserDetails(): void {
    this.userSharing.getUserDetails();
    this.userSharing.sharedUser.subscribe((user: UserDetails) => this.user = user);



    // this.userService.getUserDetails().subscribe({
    //   next: (user: UserDetails) => {
    //     this.user.username = user.username;
    //     this.user.email = user.email;
    //     this.user.profilePictureUrl = user.profilePictureUrl;
    //   },
    //   error: (err: any) => console.log(err),
    // });
  }



}
