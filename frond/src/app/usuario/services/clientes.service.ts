import { Injectable } from '@angular/core';
import { IClient, IClientRegister, IClientUpdate } from '../interfaces/client';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { catchError, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  clientForm?: IClient;

  constructor(private http: HttpClient) { }

  get currentClientForm():IClient|undefined {
    if ( !this.clientForm ) return undefined;
    return structuredClone( this.clientForm );
  }

  getClientes() {
    return this.http.get<IClient[]>(`${environment.urlApi}clients`).pipe(
      catchError(error => {throw error})
    );
  }

  createCliente(client: IClientRegister) {
    return this.http.post(`${environment.urlApi}client`, client).pipe(
      map( response => response),
      catchError(error => {throw error})
    );
  }

  updateCliente(client: IClientUpdate, id: number) {
    return this.http.put(`${environment.urlApi}client/${id}`, client).pipe(
      map( response => response),
      catchError(error => {throw error})
    );
  }

  deleteCliente(id: number) {
    return this.http.delete(`${environment.urlApi}client/${id}`).pipe(
      catchError(error => {throw error})
    );
  }

  selectClientForm(client: IClient) {
    this.clientForm = client;
  }

  resetClientForm() {
    this.clientForm = undefined;
  }

}
