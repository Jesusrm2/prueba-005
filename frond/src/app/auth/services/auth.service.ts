import { Injectable } from '@angular/core';
import { UserLogin, Usuario, UsuarioRegister } from '../interfaces/usuario';
import { environment } from '../../../environments/environment';
import  {catchError, BehaviorSubject , tap, map} from 'rxjs';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  currentUserLoginOn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  private usuario?: Usuario;
  constructor(private http: HttpClient) { 
    this.currentUserLoginOn=new BehaviorSubject<boolean>(sessionStorage.getItem("user")!=null);
  }
  get currentUser():Usuario|undefined {
    if ( !this.usuario ) return undefined;
    return structuredClone( this.usuario );
  }
  loginUser(userDetails: UserLogin) {
    return this.http.post<Usuario>(`${environment.urlHost}auth/login`, userDetails)
    .pipe(
      tap(() => this.currentUserLoginOn.next(true)),
      tap( usuario => this.usuario = usuario),
      tap(userData => sessionStorage.setItem("id", userData.userid.toString())),
      map(response => response),
      catchError( error => {throw error})
    );
  }
  registerUser(userDetails: UsuarioRegister) {
    const id: number = Number(sessionStorage.getItem('id'));
    return this.http.post<Usuario>(`${environment.urlApi}user/${id}`, userDetails).pipe(
      map(response => response),
      catchError(error => {throw error})
    );
  }
  //Actualizar 
  updateUser(userDetails: UsuarioRegister, id: number) {
    return this.http.put<Usuario>(`${environment.urlApi}user/${id}`, userDetails).pipe(
      map(response => response),
      catchError(error => {throw error})
    );
  }
  logout(){
    const id: number = Number(sessionStorage.getItem('id'));
    sessionStorage.clear();
    this.currentUserLoginOn.next(false);
    return this.http.post(`${environment.urlHost}auth/logout/${id}`,{})
    .pipe(
      map(response => response),
      catchError(error => {throw error})
    );
  }
}

