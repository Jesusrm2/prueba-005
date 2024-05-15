import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AdminClientsComponent } from '../../pages/admin-clients/admin-clients.component';
import { AdminCajaService } from '../../services/admin-caja.service';
import { IListContracts } from '../../interfaces/contract';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-cambio-servicio',
  templateUrl: './cambio-servicio.component.html'
})
export class CambioServicioComponent {

  listContracts: IListContracts[] = [];  
  selectContract?: IListContracts;
  form: FormGroup;
  isLoading = false;

  servicesSelect = [
    { id: 1, name: 'Combo 1, Velocidad: 100 Mbps - 100$' },
    { id: 2, name: 'Combo 2, Velocidad: 200 Mbps - 200$' },
    { id: 3, name: 'Combo 3, Velocidad: 300 Mbps - 300$' },
  ];

 constructor(
    private adminCajaService: AdminCajaService,
    private msgService: MessageService,
    private router: Router,
    private fb: FormBuilder,
  ) { 
    this.form = this.fb.group({
      serviceid: ['', [Validators.required]],
    });
  }
  get serviceid() {
    return this.form.controls['serviceid'];
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

  submit() {

    if (this.form.invalid) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Complete los campos requeridos' });
      return;
    }


    if (!this.selectContract) {
      this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Seleccione un contrato' });
      return;
    }

    this.isLoading = true;

    this.adminCajaService.changeService(this.selectContract.contractid, this.serviceid.value).subscribe({
      next: () => {
        this.msgService.add({ severity: 'success', summary: 'Exito', detail: 'Servicio cambiado correctamente' });
        this.router.navigate(['/admin/clients']);
      },
      error: (error) => {
        console.log(error);
        this.msgService.add({ severity: 'error', summary: 'Error', detail:`${error.error.text}` })
        this.isLoading = false
      },
      complete: () => {
        this.isLoading = false;
        this.loadContracts();
      }
    });
    

  }

}
