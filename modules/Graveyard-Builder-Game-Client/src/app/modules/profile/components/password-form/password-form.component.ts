import { CustomValidator } from './../../validators/custom-register-validator';

import { UserService } from './../../../../shared/services/user.service';
import { SnackBarLogService } from './../../../../core/services/snack-bar-log.service';
import { FormGroup, FormControl, Validators, FormGroupDirective } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-password-form',
  templateUrl: './password-form.component.html',
  styleUrls: ['./password-form.component.sass']
})
export class PasswordFormComponent implements OnInit {
  passwordForm: FormGroup;
  currentPassword: FormControl;
  newPassword: FormControl;
  verifyNewPassword: FormControl;


  // -------------------- Constructor --------------------
  constructor(
    private userService: UserService,
    private snackBar: SnackBarLogService
  ) {
    this.currentPassword = new FormControl('', [Validators.required]);
    this.newPassword = new FormControl('', [Validators.required, Validators.minLength(6)]);
    this.verifyNewPassword = new FormControl('', [Validators.required]);
    this.passwordForm = new FormGroup({
      currentPassword: this.currentPassword,
      newPassword: this.newPassword,
      verifyNewPassword: this.verifyNewPassword
    }, [CustomValidator.checkPassword]);
  }


  // -------------------- Main Methods --------------------
  ngOnInit(): void { }

  updatePassword(formDirective: FormGroupDirective): void {
    const formData = this.passwordForm.value;
    this.userService.updatePassword(formData.currentPassword, formData.newPassword).subscribe({
      next: () => {
        this.snackBar.openSnackBar('Password updated successfully', 'Close');
        this.passwordForm.reset();
        formDirective.resetForm();
      },
      error: (error) => {
        console.log('Error updating password: ', error);
        const errorMessage: string = error.error.message;
        if (errorMessage.includes('Wrong password'))
          this.snackBar.openSnackBar('Invalid current password', 'Close');
        else
          this.snackBar.openSnackBar('Error updating password', 'Close');
      }
    });
  }

}
