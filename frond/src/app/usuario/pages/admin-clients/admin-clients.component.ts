import { Component } from '@angular/core';
import { IClient } from '../../interfaces/client';
import { ClientesService } from '../../services/clientes.service';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-clients',
  templateUrl: './admin-clients.component.html',
})
export class AdminClientsComponent {
  listClients: IClient[] = [];
  isLoading = false;

  constructor(
    private clientesService: ClientesService,
    private msgService: MessageService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.loadClients();
  }
  loadClients() {
    this.isLoading = true;
    this.clientesService.getClientes().subscribe(
      {
        next: (clientes) => {
          this.listClients = clientes;
        },
        error: (error) => {
          this.msgService.add({ severity: 'error', summary: 'Error', detail:`${error.error.text}` })
          this.isLoading = false
        },
        complete: () => this.isLoading = false,
      }
    );
  }
  formClient(client?: IClient) {
    if (client) {
      this.clientesService.clientForm = client;
      this.router.navigate(['/form-client']);
    } else {
      this.router.navigate(['/form-client']);
    }
  }

  deleteClient(client: IClient) {
    this.isLoading = true;
    this.clientesService.deleteCliente(client.clientid).subscribe({
      next: () => {
        this.msgService.add({ severity: 'success', summary: 'Éxito', detail: "Cliente eliminado" });
        this.loadClients();
      },
      error: () => {
        this.msgService.add({ severity: 'error', summary: 'Error', detail: "Error al eliminar el cliente" })
        this.isLoading = false
      },
      complete: () => this.isLoading = false,
    });
  }
}
