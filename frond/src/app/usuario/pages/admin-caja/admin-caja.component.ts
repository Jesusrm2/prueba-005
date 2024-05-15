import { Component } from '@angular/core';
import { ITurnsCashs } from '../../interfaces/tuns';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { AdminCajaService } from '../../services/admin-caja.service';
import { IClient } from '../../interfaces/client';
import { map } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IContractRegister } from '../../interfaces/contract';

@Component({
  selector: 'app-admin-caja',
  templateUrl: './admin-caja.component.html'
})
export class AdminCajaComponent {

  registerForm: FormGroup;

  listTurnos: ITurnsCashs[] = [];
  turnoSelected?: ITurnsCashs;

  listClientes: IClient[] = [];
  clienteSelected?: IClient;

  isLoading = false;
  servicesSelect = [
    { id: 1, name: 'Combo 1, Velocidad: 100 Mbps - 100$' },
    { id: 2, name: 'Combo 2, Velocidad: 200 Mbps - 200$' },
    { id: 3, name: 'Combo 3, Velocidad: 300 Mbps - 300$' },
  ];
  methodPaymentsSelect = [
    { id: 1, name: 'Efectivo' },
    { id: 2, name: 'Tarjeta de Credito' },
    { id: 3, name: 'Transferencia' },
  ];
  devicesSelect = [
    { id: 1, name: 'Router' },
    { id: 2, name: 'Modem' },
    { id: 3, name: 'Switch' },
    { id: 4, name: 'Access Point'}
  ];
  constructor(
    private adminCajaService: AdminCajaService,
    private msgService: MessageService,
    private router: Router,
    private fb: FormBuilder,
  ) { 
    this.registerForm = this.fb.group({
      startdate: ['', [Validators.required]],
      enddate: ['', [Validators.required]],
      serviceid: ['', [Validators.required]],
      methodpaymentid: ['', [Validators.required]],
      clientid: ['', [Validators.required]],
      devices: ['', [Validators.required]],
    });
  }

  get startdate() {
    return this.registerForm.controls['startdate'];
  }
  get enddate() {
    return this.registerForm.controls['enddate'];
  }
  get serviceid() {
    return this.registerForm.controls['serviceid'];
  }
  get methodpaymentid() {
    return this.registerForm.controls['methodpaymentid'];
  }
  get clientid() {
    return this.registerForm.controls['clientid'];
  }
  get devices() {
    return this.registerForm.controls['devices'];
  } 



  ngOnInit() {
    this.loadTurnos();
    this.loadClientes();
  }

  loadTurnos() {
    this.isLoading = true;
    this.adminCajaService.getTurnosCajas().subscribe(
      {
        next: (turnos) => {
          this.listTurnos = turnos.map(turno => {
            return {...turno, label: `${turno.description}`};
          })
        },
        error: (error) => {
          this.msgService.add({ severity: 'error', summary: 'Error', detail:`${error.error.text}` })
          this.isLoading = false
        },
        complete: () => this.isLoading = false,
      }
    );
  }

  loadClientes() {
    this.isLoading = true;
    this.adminCajaService.getClientes().subscribe(
      {
        next: (clientes) => {
          this.listClientes = clientes.map(cliente => {
            return {...cliente, label: `${cliente.name} ${cliente.lastname} - ${cliente.identification}`};
          })
        },
        error: (error) => {
          this.msgService.add({ severity: 'error', summary: 'Error', detail:`${error.error.text}` })
          this.isLoading = false
        },
        complete: () => this.isLoading = false,
      }
    );
  }

  submitDetails() {
    this.isLoading = true;
    const data = this.registerForm.value;
    if (this.turnoSelected) {
      this.adminCajaService.createContract(this.turnoSelected.turnid, data as IContractRegister ).subscribe(
        {
          next: () => {
            this.msgService.add({ severity: 'success', summary: 'Success', detail: 'Contrato realizado correctamente' });
          },
          error: (error) => {
            this.msgService.add({ severity: 'error', summary: 'Error', detail:`${error.error}` })
            this.isLoading = false;
          },
          complete: () => {
            this.registerForm.reset();
            this.isLoading = false;
          },
        }
      );
    } else {
      this.msgService.add({ severity: 'error', summary: 'Error', detail:"Seleccione el turno" })
    }
  }

  onTurnoChane(event: any) {
    this.turnoSelected = event.value;
  }

  onClienteChange(event: any) {
    console.log(event.value);
    this.clienteSelected = event.value;
    this.clientid.setValue(event.value.clientid);
    
  }

}
