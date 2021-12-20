import { AuthService } from './auth.service';
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }


  canActivate(): boolean {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['']);
      return false;
    }
    return true;
  }

}
