
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class CustomValidator {
  static checkPassword(control: AbstractControl): ValidationErrors | null {
    if (control.get('password')?.value !== control.get('passwordConfirmation')?.value)
      return { differentPassword: true };

    return null;
  }

}