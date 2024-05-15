import { Component } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../auth/services/auth.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { Usuario, UsuarioRegister } from '../../../auth/interfaces/usuario';
import { AdminUsuariosService } from '../../services/admin-usuarios.service';

@Component({
  selector: 'app-form-usuario',
  templateUrl: './form-usuario.component.html'
})
export class FormUsuarioComponent {
  isLoading = false;
  rolesSelect = [ 
  {id: 2, name: 'Gestor'}, 
  {id: 3, name: 'Cajero'}]; 
  registerForm: FormGroup;

 
  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private messageService: MessageService,
    private router: Router,
    private adminUsuariosService: AdminUsuariosService,
  ) { 
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20), Validators.pattern(/^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$/)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(30), Validators.pattern(/^(?=.*[0-9])(?=.*[A-Z]).{8,30}$/)]],
      rol_rolid: ['', Validators.required]
    });
    if(this.userForm){
      this.registerForm.patchValue(this.userForm);
    }
  }
  get usuario(): Usuario | undefined {  
    return this.authService.currentUser;
  }
  get userForm(): Usuario | undefined { 
    return this.adminUsuariosService.currentUserForm;
  }
  get email() {
    return this.registerForm.controls['email'];
  }

  get password() {
    return this.registerForm.controls['password'];
  }

  get username() {
    return this.registerForm.controls['username'];
  }

  get rol_rolid() {
    return this.registerForm.controls['rol_rolid'];
  }

  submitDetails() {
    this.isLoading = true;
    const postData = { ...this.registerForm.value, rol_rolid: Number(this.registerForm.value.rol_rolid) };
    console.log(this.userForm);
    if(this.userForm){
      this.authService.updateUser(postData as UsuarioRegister, this.userForm.userid).subscribe(
        {
          next: () => {
            this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Usuario actualizado'});  
          },
          error: error => {
            console.log(error);
            this.messageService.add({ severity: 'error', summary: 'Error', detail: `${error.error}`});
            this.isLoading = false;
          },
          complete: () => {
            this.isLoading = false;
          }
        }
      );
    }else{
      if (this.usuario) {
        this.authService.registerUser(postData as UsuarioRegister ).subscribe(
          {
            next: () => {
              this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Usuario registrado'});  
            },
            error: error => {
              console.log(error);
              this.messageService.add({ severity: 'error', summary: 'Error', detail: `${error.error}`});
              this.isLoading = false;
            },
            complete: () => {
              this.registerForm.reset();
              this.isLoading = false;
            }
          }
        );
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se ha podido obtener el usuario'});
        this.isLoading = false;
      }
    }
  }
  aprobar(){
    this.isLoading = true;
   if(this.userForm){
    this.adminUsuariosService.aprobarUsuario(this.userForm.userid).subscribe(
      {
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Usuario aprobado'});  
        },
        error: error => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: `${error.error}`});
          this.isLoading = false;
        },
        complete: () => {
          this.isLoading = false;
        }
      }
    );
   }
  } 
  cancel(){
    const id: number = Number(sessionStorage.getItem('id'));
    if(id === 1){
      this.router.navigate(['/admin-usuarios']);
    }else{
      this.router.navigate(['/form-usuario']);
    }

    this.adminUsuariosService.resetUserForm();

  }
}
