import { Component } from '@angular/core';
import { IListAtention } from '../../interfaces/attention';
import { AtendidosService } from '../../services/atendidos.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {

  listAttention: IListAtention[] = [];
  isLoading = false;

  constructor(

    private atendidosService: AtendidosService,
    private msgService: MessageService,
  ) { }

  ngOnInit() {
    this.loadAttention();
  }

  loadAttention() {
    this.isLoading = true;
    this.atendidosService.getAttentions().subscribe(
      {
        next: (atention) => {
          this.listAttention = atention;
        },
        error: (error) => {
          this.msgService.add({ severity: 'error', summary: 'Error', detail:`${error.error.text}` })
          this.isLoading = false
        },
        complete: () => this.isLoading = false
      }
    );
  }
}
