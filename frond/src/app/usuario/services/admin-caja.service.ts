import { Injectable } from '@angular/core';
import { ITurnsCashs } from '../interfaces/tuns';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { catchError, map } from 'rxjs';
import { IClient } from '../interfaces/client';
import { IContractRegister, IListContracts } from '../interfaces/contract';

@Injectable({
  providedIn: 'root'
})
export class AdminCajaService {

  turnoSelected?: ITurnsCashs;
  clienteSelected?: IClient;

  constructor(private http: HttpClient) { }

  get currentTurno(): ITurnsCashs | undefined {
    if (!this.turnoSelected) return undefined;
    return structuredClone(this.turnoSelected);
  }

  get currentClient(): IClient | undefined {
    if (!this.clienteSelected) return undefined;
    return structuredClone(this.clienteSelected);
  }

  getTurnosCajas() {
    return this.http.get<ITurnsCashs[]>(`${environment.urlApi}contract/turns`).pipe(
      catchError(error => { throw error })
    );
  }

  getContratos() {  
    return this.http.get<IListContracts[]>(`${environment.urlApi}contracts`).pipe(
      catchError(error => { throw error })
    );
  }

  getClientes() {
    return this.http.get<IClient[]>(`${environment.urlApi}clients`).pipe(
      catchError(error => { throw error })
    );
  }

  createContract(id: number, data: IContractRegister) {
    return this.http.post(`${environment.urlApi}contract/${id}`, data).pipe(
      map(response => response),
      catchError(error => { throw error })
    );
  }

  changeService(idcontract: number, idservice: number) {
    return this.http.post(`${environment.urlApi}contract/change/${idcontract}/service${idservice}`, {}).pipe(
      map(response => response),
      catchError(error => { throw error })
    );
  }

  changePaymentMethod(idcontract: number, idpaymentmethod: number) {
    return this.http.post(`${environment.urlApi}contract/change/${idcontract}/paymentmethod/${idpaymentmethod}`, {}).pipe(
      map(response => response),
      catchError(error => { throw error })
    );
  }

  cancelContract(id: number) {
    return this.http.post(`${environment.urlApi}contract/cancel/${id}`, {}).pipe(
      map(response => response),
      catchError(error => { throw error })
    );
  }


  selectTurno(turno: ITurnsCashs) {
    this.turnoSelected = turno;
  }
  resetTurno() {
    this.turnoSelected = undefined;
  }
  selectClient(client: IClient) {
    this.clienteSelected = client;
  }
  resetClient() {
    this.clienteSelected = undefined;
  }
}
