import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { CajaService } from '../../services/caja.service';
import { ICash } from '../../interfaces/cash';
import { Usuario } from '../../../auth/interfaces/usuario';

@Component({
  selector: 'app-asignar-caja',
  templateUrl: './asignar-caja.component.html'
})
export class AsignarCajaComponent {
  isLoading = false;

  cajas: ICash[] = [];
  selectedCaja?: ICash;

  usuariosCajeros: Usuario[] = [];
  selectedUsuarioCajero?: Usuario;

  constructor(
    private fb: FormBuilder,
    private msgService: MessageService,
    private cajaService: CajaService
  ) { }

  asignarCajaUsuario() {

    if (this.selectedCaja && this.selectedUsuarioCajero) {
      this.cajaService.assignCajaToCajero(this.selectedCaja.cashid, this.selectedUsuarioCajero.userid).subscribe({
        next: () => {
          this.msgService.add({ severity: 'success', summary: 'Success', detail: 'Caja asignada a cajero exitosamente' });
        },
        error: (error) => {
          console.log(error);
          this.msgService.add({ severity: 'error', summary: 'Error', detail: `${error.error}`	 });
        }
      });
    } else {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Seleccione una caja y un usuario cajero' });
    }


  }

  ngOnInit() {
    this.isLoading = true;
    this.loadCajas();
    this.loadUusariosCajeros();
  }

  loadCajas() {
    this.cajaService.getCajas().subscribe(
      {
        next: (data) => {
          this.cajas = data.map(caja => {
            return {...caja, label: `${caja.cashdescription}`};
          });
        },
        error: () =>{ 
          this.msgService.add({ severity: 'error', summary: 'Error', detail: "Error al cargar las cajas"})
          this.isLoading = false
        }  ,
        complete: () => this.isLoading = false,  
      }
    );
  }

  loadUusariosCajeros() {
    this.cajaService.getUsuariosCajeros().subscribe(
      {
        next: (data) => {
          this.usuariosCajeros = data.map(usuario => {
            return {...usuario, label: `${usuario.username} - ${usuario.email}`};
          });
        },
        error: (error) =>{ 
          this.msgService.add({ severity: 'error', summary: 'Error', detail: `${error.error.text}`})
          this.isLoading = false
        }  ,
        complete: () => this.isLoading = false,  
      }
    );
  }

  onCajaChange(event: any) {
    this.selectedCaja = event.value;
  }

  onUsuarioCajeroChange(event: any) {
    this.selectedUsuarioCajero = event.value;
  }


}
