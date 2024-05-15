import { Component } from '@angular/core';
import { IListContracts } from '../../interfaces/contract';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminCajaService } from '../../services/admin-caja.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cancelar-contrato',
  templateUrl: './cancelar-contrato.component.html'
})
export class CancelarContratoComponent {

  listContracts: IListContracts[] = [];  
  selectContract?: IListContracts;

  isLoading = false;

  estadoSelect = [
    { id: 1, name: 'VIG – Contrato vigente' },
    { id: 2, name: 'SUS – Contrato sustituido' },
    { id: 3, name: 'CAN – Contrato cancelado' },
  ];
  constructor(
    private adminCajaService: AdminCajaService,
    private msgService: MessageService,

  
  ) { 
 
  }
 
  ngOnInit() {
    this.loadContracts();
  }
  loadContracts() {
    this.isLoading = true;
    this.adminCajaService.getContratos().subscribe(
      {
        next: (contracts) => {

          this.listContracts = contracts.map(contract => {
            return {
              ...contract,
              label: `${contract.client_clientid.name} ${contract.client_clientid.lastname} - ${contract.client_clientid.identification} - Servicio: ${contract.service_serviceid.servicedescription}  - metodo de pago: ${contract.methodpayment_methodpaymentid.description} - ${contract.statuscontract_statuscontractid.description}`,
            }});
          
        },
        error: (error) => {
          this.msgService.add({ severity: 'error', summary: 'Error', detail:`${error.error.text}` })
          this.isLoading = false
        },
        complete: () => this.isLoading = false
      }
    );
  }
  onContractChange(event: any) {
    this.selectContract = event.value;
  }
  cancelContract() {
    if (this.selectContract) {
      this.isLoading = true;
      this.adminCajaService.cancelContract(this.selectContract.contractid).subscribe({
        next: () => {
          this.msgService.add({ severity: 'success', summary: 'Exito', detail: 'Contrato cancelado' });
          this.loadContracts();
        },
        error: (error) => {
          this.msgService.add({ severity: 'error', summary: 'Error', detail:`${error.error.text}` })
          this.isLoading = false
        },
        complete: () => this.isLoading = false
      });
    } else {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Seleccione un contrato' });
    }
  }
}
