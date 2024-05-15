import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { BehaviorSubject, catchError, map } from 'rxjs';
import { Usuario } from '../../auth/interfaces/usuario';

@Injectable({
  providedIn: 'root'
})
export class ItemMenuService {

  constructor(private http: HttpClient) { }
  
  private _visible = new BehaviorSubject<boolean>(true);
  visible$ = this._visible.asObservable();

  toggleVisible() {
    this._visible.next(!this._visible.value);
  }

  getUsuario() {
    const id: number = Number(sessionStorage.getItem('id'));
    return this.http.get<Usuario>(`${environment.urlApi}user/${id}`).pipe(
      map(res => res),
      catchError(err => { throw err; })
    );
  }

}
