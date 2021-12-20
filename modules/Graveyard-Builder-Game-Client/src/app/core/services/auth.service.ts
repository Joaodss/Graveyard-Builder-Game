import { SnackBarLogService } from './snack-bar-log.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, from, ReplaySubject, shareReplay, Subject, tap } from 'rxjs';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = "http://localhost:8000"
  public isUserLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.isLoggedIn());

  constructor(
    private http: HttpClient,
    private snackBar: SnackBarLogService
  ) { }

  public login(username: string, password: string) {
    const requestAuthBody = `username=${username}&password=${password}`;
    const requestOptions = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };
    return this.http.post<Object>(this.baseUrl + '/login', requestAuthBody, requestOptions).pipe(
      tap(res => this.setSession(res)),
      shareReplay()
    )
  }

  public logout() {
    localStorage.removeItem("access_token");
    localStorage.removeItem("expires_at");
    if(this.isUserLoggedIn === undefined) {return}
    this.isUserLoggedIn.next(false);
  }

  public isLoggedIn(): boolean {
    if (localStorage.getItem("access_token") == null || localStorage.getItem("expires_at") == null)
      return false;
    if (moment().isBefore(this.getExpiration()))
      return true;
    this.logout();
    this.snackBar.openSnackBar("Session ended. Please login to continue", "Close");
    return false;
  }


  private setSession(authResult: any) {
    const expiresAt = moment(0).add(authResult.expires_at);
    localStorage.setItem('access_token', authResult.access_token);
    localStorage.setItem("expires_at", JSON.stringify(expiresAt));
    this.isUserLoggedIn.next(true);
    this.isLoggedIn();
  }

  private getExpiration() {
    const expiration = localStorage.getItem("expires_at");
    const expiresAt = JSON.parse(expiration || "0");
    return moment(expiresAt);
  }

}
