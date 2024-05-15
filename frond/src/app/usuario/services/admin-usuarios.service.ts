import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario, UsuarioRegister } from '../../auth/interfaces/usuario';
import { environment } from '../../../environments/environment';
import { catchError, map, tap } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AdminUsuariosService {

  userForm?: Usuario; 

  constructor(private http: HttpClient) { }

  get currentUserForm():Usuario|undefined {
    if ( !this.userForm ) return undefined;
    return structuredClone( this.userForm );
  }
  getUsuarios() {
    return this.http.get<Usuario[]>(`${environment.urlApi}users`).pipe(
      catchError(error => {throw error})
    );
  }

  deleteUsuario(id: number) {
    return this.http.delete(`${environment.urlApi}user/${id}`).pipe(
      map( response => response),
      catchError(error => {throw error})
    );
  }

  //aprobar usuario
  aprobarUsuario(id: number) {
    return this.http.put(`${environment.urlApi}user/aprobar/${id}`, {}).pipe(
      map( response => response),
      catchError(error => {throw error})
    );
  }

  selectUserForm(user: Usuario) {
    this.userForm = user;
  }
  resetUserForm() { 
    this.userForm = undefined;
  }
}
