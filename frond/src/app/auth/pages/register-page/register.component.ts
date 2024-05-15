import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { UsuarioRegister } from '../../interfaces/usuario';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  isLoading = false;
  registerForm = this.fb.group({
    nombres: ['', Validators.required],
    apellidos: ['', Validators.required],
    correo: ['', [Validators.required, Validators.email]],
    contrasena: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(10), Validators.pattern(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@_]).{1,10}$/)]]
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private messageService: MessageService,
    private router: Router
  ) { }

  get correo() {
    return this.registerForm.controls['correo'];
  }

  get contrasena() {
    return this.registerForm.controls['contrasena'];
  }

  get nombres() {
    return this.registerForm.controls['nombres'];
  }

  get apellidos() {
    return this.registerForm.controls['apellidos'];
  }



  submitDetails() {
    
  }
}
