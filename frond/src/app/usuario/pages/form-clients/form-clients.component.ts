import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ClientesService } from '../../services/clientes.service';
import { IClient, IClientRegister, IClientUpdate } from '../../interfaces/client';


@Component({
  selector: 'app-form-clients',
  templateUrl: './form-clients.component.html',
})
export class FormClientsComponent {
  isLoading = false;
 registerForm: FormGroup;
 clientDetail: IClient | any;
  constructor(
    private fb: FormBuilder,
    private clientesService:ClientesService,
    private messageService: MessageService,
    private router: Router,

  ) { 
    this.registerForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      lastname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      identification: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email]],
      phonenumber: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(10)]],
      address: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      referenceaddress: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    });
    if(this.clientForm){
      this.registerForm.patchValue(this.clientForm);
      if (this.registerForm) {
        this.registerForm.get('identification')?.disable();
      }
    }
  
  }

  get clientForm(): IClient | undefined{
    return this.clientesService.clientForm;
  }

  get name() {
    return this.registerForm.controls['name'];
  }

  get lastname() {
    return this.registerForm.controls['lastname'];
  }

  get identification() {
    return this.registerForm.controls['identification'];
  }

  get email() {
    return this.registerForm.controls['email'];
  }

  get phonenumber() {
    return this.registerForm.controls['phonenumber'];
  }

  get address() {
    return this.registerForm.controls['address'];
  }

  get referenceaddress() {
    return this.registerForm.controls['referenceaddress'];
  }

  submitDetails() {
    this.isLoading = true;
    this.clientDetail = this.registerForm.getRawValue();
 
    if(this.clientForm){

      delete this.clientDetail.identification;
      this.clientesService.updateCliente(this.clientDetail as IClientUpdate, this.clientForm.clientid).subscribe(
        {
          next: () => {
            this.messageService.add({ severity: 'success', summary: 'Éxito', detail: 'Cliente actualizado'});  
          },
          error: error => {
            console.log(error);
            this.messageService.add({ severity: 'error', summary: 'Error', detail: `${error.error.text}`});
            this.isLoading = false;
          },
          complete: () => {
            this.isLoading = false;
          }
        }
      );
    } else {

      this.clientesService.createCliente(  this.clientDetail as IClientRegister).subscribe(
        {
          next: () => {
            this.messageService.add({ severity: 'success', summary: 'Éxito', detail: 'Cliente creado'});  
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
    }
  }

  cancel() {
    this.router.navigate(['/mantenimiento-clientes']);
    this.clientesService.resetClientForm();
  }


}
