import { Component } from '@angular/core';
import { IListContracts } from '../../interfaces/contract';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { AdminCajaService } from '../../services/admin-caja.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cambio-metodo-pago',
  templateUrl: './cambio-metodo-pago.component.html'
})
export class CambioMetodoPagoComponent {

  listContracts: IListContracts[] = [];  
  selectContract?: IListContracts;
  form: FormGroup;
  isLoading = false;
  methodPaymentsSelect = [
    { id: 1, name: 'Efectivo' },
    { id: 2, name: 'Tarjeta de Credito' },
    { id: 3, name: 'Transferencia' },
  ];
  constructor(
    private adminCajaService: AdminCajaService,
    private msgService: MessageService,
    private router: Router,
    private fb: FormBuilder,
  ) { 
    this.form = this.fb.group({
      methodpaymentid: ['', [Validators.required]],
    });
  }

  get methodpaymentid() {
    return this.form.controls['methodpaymentid'];
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
              label: `${contract.client_clientid.name} ${contract.client_clientid.lastname} - ${contract.client_clientid.identification} - Servicio: ${contract.service_serviceid.servicedescription}  - metodo de pago: ${contract.methodpayment_methodpaymentid.description}`,            }});
          
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

  submit() {
    if (this.methodpaymentid && this.selectContract) {
      this.isLoading = true;
      this.adminCajaService.changePaymentMethod(this.selectContract.contractid, this.methodpaymentid.value).subscribe(
        {
          next: (res) => {
            this.msgService.add({ severity: 'success', summary: 'Success', detail: 'Metodo de pago cambiado' });
            this.isLoading = false;
          },
          error: (error) => {
            this.msgService.add({ severity: 'error', summary: 'Error', detail:`${error.error.text}` })
            this.isLoading = false
          },
          complete: () => {
            this.isLoading = false;
            this.loadContracts();
          }
        }
      )
    }
  }


}
