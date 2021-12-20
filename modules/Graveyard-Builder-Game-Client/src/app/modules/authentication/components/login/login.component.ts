import { UserSharingService } from './../../../../shared/services/user-sharing.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { AuthService } from './../../../../core/services/auth.service';
import { SnackBarLogService } from './../../../../core/services/snack-bar-log.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  username: FormControl;
  password: FormControl;


  // -------------------- Constructor --------------------
  constructor(
    private auth: AuthService,
    private userSharing: UserSharingService,
    private snackBar: SnackBarLogService,
    private router: Router
  ) {
    this.username = new FormControl('', [Validators.required]);
    this.password = new FormControl('', [Validators.required]);
    this.loginForm = new FormGroup({
      username: this.username,
      password: this.password
    });
  }


  // -------------------- Main Methods --------------------
  ngOnInit(): void { }

  login(formDirective: FormGroupDirective): void {
    if (!this.loginForm.valid) return;
    const formData = this.loginForm.value;
    this.auth.login(formData.username, formData.password).subscribe({
      next: () => {
        this.router.navigate(['/menu']);
        this.loginForm.reset();
        formDirective.resetForm();
        this.snackBar.openSnackBar('Login Successful', 'Close');
        this.userSharing.getUserDetails();
      },
      error: (error) => {
        this.loginForm.setValue({ username: this.username.value, password: '' });
        this.snackBar.openSnackBar(
          error.status === 403 ?
            'Invalid Credentials' :
            'Login Failed',
          'Close'
        );
      }
    });
  }

  toRegister(): void {
    this.router.navigate(['/register']);
  }

}
