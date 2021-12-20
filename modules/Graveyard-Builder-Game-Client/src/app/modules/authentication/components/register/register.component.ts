import { SnackBarLogService } from './../../../../core/services/snack-bar-log.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators, FormGroupDirective } from '@angular/forms';
import { Component, OnInit, } from '@angular/core';
import { Observable, forkJoin } from 'rxjs';
import { RegistrationService } from './../../services/registration.service';
import { CustomValidator } from './../../validators/custom-register-validator';



@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  username: FormControl;
  email: FormControl;
  password: FormControl;
  passwordConfirmation: FormControl;


  // -------------------- Constructor --------------------
  constructor(
    private registrationService: RegistrationService,
    private snackBar: SnackBarLogService,
    private router: Router
  ) {
    this.username = new FormControl('', [Validators.required, Validators.minLength(3)]);
    this.email = new FormControl('', [Validators.required, Validators.email]);
    this.password = new FormControl('', [Validators.required, Validators.minLength(6)]);
    this.passwordConfirmation = new FormControl('');
    this.registerForm = new FormGroup({
      username: this.username,
      email: this.email,
      password: this.password,
      passwordConfirmation: this.passwordConfirmation
    }, [CustomValidator.checkPassword]);
  }


  // -------------------- Main Methods --------------------
  ngOnInit(): void { }

  validateAndRegister(formDirective: FormGroupDirective): void {
    if (!this.registerForm.valid) return;
    const formData = this.registerForm.value;
    let isValidUsername: Observable<boolean> = this.registrationService.isValidUsername(formData.username);
    let isValidEmail: Observable<boolean> = this.registrationService.isValidEmail(formData.email);
    forkJoin([isValidUsername, isValidEmail]).subscribe({
      next: (result) => {
        if (result[0] && result[1]) {
          this.registerNewUser(formDirective);
        } else if (!result[0] && !result[1]) {
          this.snackBar.openSnackBar('Username and email are already taken.', 'Close');
        } else if (!result[0]) {
          this.snackBar.openSnackBar('Username is already taken.', 'Close');
        } else if (!result[1]) {
          this.snackBar.openSnackBar('Email is already taken.', 'Close');
        }
      },
      error: () => this.snackBar.openSnackBar('Unable to register user. Try again later.', 'Close')
    });
  }

  registerNewUser(formDirective: FormGroupDirective): void {
    this.registrationService.register(this.username.value, this.email.value, this.password.value).subscribe({
      next: () => {
        this.router.navigate(['/login']);
        this.registerForm.reset();
        formDirective.resetForm();
        this.snackBar.openSnackBar('Registration successful. You can now login.', 'Close');
      },
      error: () => this.snackBar.openSnackBar('Unable to register user. Try again later.', 'Close')
    });
  }

}
