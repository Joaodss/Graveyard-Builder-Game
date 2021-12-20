import { UserDetails } from './../models/user.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  readonly baseUrl: string = "http://localhost:8000/api/v1/profiles";


  constructor(
    private http: HttpClient
  ) { }


  public getUserDetails(): Observable<UserDetails> {
    return this.http.get<UserDetails>(this.baseUrl + '/user');
  }

  public updateGold(gold: number): Observable<UserDetails> {
    const updateUserBody = { "gold": gold };
    return this.http.post<any>(this.baseUrl + '/update/all', updateUserBody);
  }

  public updateImage(imageUrl: string): Observable<UserDetails> {
    const updateUserBody = { "profilePictureUrl": imageUrl };
    return this.http.post<any>(this.baseUrl + '/update/all', updateUserBody);
  }

  public updatePassword(currentPassword: string, newPassword: string): Observable<any> {
    const updateUserBody = { "oldPassword": currentPassword, "newPassword": newPassword };
    return this.http.post<any>(this.baseUrl + '/update/password', updateUserBody);
  }

}
