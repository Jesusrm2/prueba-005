import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { BehaviorSubject, catchError, map, tap } from 'rxjs';
import { ITurn, ITurnrRegister } from '../interfaces/tuns';
@Injectable({
  providedIn: 'root'
})
export class TurnosService {
  constructor(private http: HttpClient) { }

  //Crud turnos
  getTurnos() {
    return this.http.get<ITurn[]>(`${environment.urlApi}turns`).pipe(
      map(response => response),
      catchError(error =>  {throw error})
    );
  }

  //Create
  createTurno(data: ITurnrRegister) {
    const id: number = Number(sessionStorage.getItem('id'));
    return this.http.post<ITurn>(`${environment.urlApi}turn/gestor/${id}`, data).pipe(
      map(response => response),
      catchError(error => { throw error })
    );
  }

  //Delete
  deleteTurno(id: number) {
    return this.http.delete(`${environment.urlApi}turn/${id}`).pipe(
      map(response => response),
      catchError(error => { throw error })
    );
  }

  //Aasignar turno a caja
  assignTurnoToCaja(idturn: number, idcash: number) {
    return this.http.post(`${environment.urlApi}turn/${idturn}/cash/${idcash}`, {}).pipe(
      map(response => response),
      catchError(error => { throw error })
    );
  }


}
