import { HelpPageComponent } from './../help-page/help-page.component';
import { UserSharingService } from './../../services/user-sharing.service';
import { UserService } from './../../services/user.service';
import { AuthService } from './../../../core/services/auth.service';
import { Router } from '@angular/router';
import { UserDetails } from './../../models/user.model';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.sass']
})
export class HeaderComponent implements OnInit {
  isLoggedIn!: boolean;
  level!: number;
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
    private userSharing: UserSharingService,
    private dialog: MatDialog
  ) { }


  ngOnInit(): void {
    this.authService.isUserLoggedIn.subscribe(
      value => {
        this.isLoggedIn = value;
        if (this.isLoggedIn === true) this.getUserSharedDetails();
      }
    );
  }

  goToProfile(): void {
    this.route.navigate(['/profile']);
  }

  goToLogin(): void {
    this.route.navigate(['/login']);
  }

  private getUserSharedDetails(): void {
    this.userSharing.getUserDetails();
    this.userSharing.sharedUser.subscribe(
      value => {
        this.user = value
        this.level = (this.user.experience / 10) / (Math.log10(this.user.experience + 10)) + 1;
      }
    );
  }

  openHelpPage(): void {
    const dialogRef = this.dialog.open(HelpPageComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

}
