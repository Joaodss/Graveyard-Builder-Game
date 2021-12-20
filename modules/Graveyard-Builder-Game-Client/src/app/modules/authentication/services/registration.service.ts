import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  readonly baseUrl: string = "http://localhost:8000/api/v1/signIn";

  constructor(
    private http: HttpClient
  ) { }


  register(username: string, email: string, password: string): Observable<any> {
    const body = { "username": username, "email": email, "password": password };
    return this.http.post<any>(this.baseUrl + "/register", body);
  }

  isValidUsername(username: string): Observable<boolean> {
    const body = { "username": username };
    return this.http.post<any>(this.baseUrl + "/validate/username", body);
  }

  isValidEmail(email: string): Observable<boolean> {
    const body = { "email": email };
    return this.http.post<any>(this.baseUrl + "/validate/email", body);
  }

}
