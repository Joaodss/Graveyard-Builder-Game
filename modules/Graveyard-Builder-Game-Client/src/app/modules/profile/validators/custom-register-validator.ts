
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class CustomValidator {
  static checkPassword(control: AbstractControl): ValidationErrors | null {
    if (control.get('newPassword')?.value !== control.get('verifyNewPassword')?.value)
      return { differentPassword: true };

    return null;
  }

}