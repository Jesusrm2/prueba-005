import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { catchError, map } from 'rxjs';
import { ICash, IRegisterCash } from '../interfaces/cash';
import { Usuario } from '../../auth/interfaces/usuario';

@Injectable({
  providedIn: 'root'
})
export class CajaService {

  constructor(private http: HttpClient) { }



  getCajas() {
    return this.http.get<ICash[]>(`${environment.urlApi}cashs`).pipe(
      map(response => response),
      catchError(error =>  {throw error})
    );
  }
   getUsuariosCajeros() {
    return this.http.get<Usuario[]>(`${environment.urlApi}users/cashier`).pipe(
      map(response => response),
      catchError(error =>  {throw error})
    );
  }
  //Create
  createCaja(data: IRegisterCash) {
    return this.http.post<ICash>(`${environment.urlApi}cash`, data).pipe(
      map(response => response),
      catchError(error => { throw error })
    );
  }

  //Delete
  deleteCaja(id: number) {
    return this.http.delete(`${environment.urlApi}cash/${id}`).pipe(
      map(response => response),
      catchError(error => { throw error })
    );
  }

  //Asignar caja a cajero
  assignCajaToCajero(idcash: number, iduser: number) {
    return this.http.post(`${environment.urlApi}cash/${idcash}/user/${iduser}`, {}).pipe(
      map(response => response),
      catchError(error => { throw error })
    );
  }

}
