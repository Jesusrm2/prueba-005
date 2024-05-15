import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  private _displayDialogs: { [key: string]: BehaviorSubject<boolean> } = {};

  getDialog(id: string) {
    if (!this._displayDialogs[id]) {
      this._displayDialogs[id] = new BehaviorSubject<boolean>(false);
    }
    return this._displayDialogs[id].asObservable();
  }

  openDialog(id: string) {
    if (!this._displayDialogs[id]) {
      this._displayDialogs[id] = new BehaviorSubject<boolean>(false);
    }
    this._displayDialogs[id].next(true);
  }

  closeDialog(id: string) {
    if (this._displayDialogs[id]) {
      this._displayDialogs[id].next(false);
    }
  }

}
