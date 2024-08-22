import {Component, EventEmitter, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration-form.component.html',
  styles: [
  ]
})
export class RegistrationFormComponent {
  @Output() closeModal = new EventEmitter<void>();
  registrationForm: FormGroup;

  constructor(private authService: AuthService, private fb: FormBuilder) {
    this.registrationForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }


  onSubmit() {
    if(this.registrationForm.valid) {
      const request = this.registrationForm.value;
      this.authService.register(request).subscribe({
        next: (response) => {
          console.log('registered', response)
          this.closeModal.emit();
        },
        error: (err) => {
          console.error('failed reg')
          return err
        }
      });
    }
  }

  onCancel() {
    this.closeModal.emit();
  }

}
