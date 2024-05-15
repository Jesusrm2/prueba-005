import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IListAtention } from '../interfaces/attention';
import { environment } from '../../../environments/environment';
import { catchError, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AtendidosService {

  constructor(private http: HttpClient) { }
  getAttentions() {
    return this.http.get<IListAtention[]>(`${environment.urlApi}attentions`).pipe(
      map(response => response),
      catchError(error =>  {throw error})
    );
  }

}
