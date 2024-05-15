import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { TurnosService } from '../../services/turnos.service';
import { ITurn, ITurnrRegister } from '../../interfaces/tuns';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ICash, IRegisterCash } from '../../interfaces/cash';
import { CajaService } from '../../services/caja.service';

@Component({
  selector: 'app-asignar-turno',
  templateUrl: './asignar-turno.component.html',
})
export class AsignarTurnoComponent {
  isLoading = false;
  //Turnos
  turnos: ITurn [] = [];
  selectedTurno?: ITurn;
  turnoForm: FormGroup;
  //Cajas 
  cajas: ICash[] = [];
  selectedCaja?: ICash;
  cajaForm: FormGroup;


  constructor(
    private fb: FormBuilder,
    private msgService: MessageService,
    private turnosService: TurnosService,
    private cajaService: CajaService
  ) {

    this.turnoForm = this.fb.group({ 
      description: ['', [Validators.required, descriptionValidator]],
      date: ['', [Validators.required]],
    });
    this.cajaForm = this.fb.group({
      cashdescription: ['', [Validators.required]],
      active: ['', [Validators.required]],
    });

   }
   get description() {
    return this.turnoForm.controls['description'];
  } 
  get date() {
    return this.turnoForm.controls['date'];
  }
  get cashdescription() {
    return this.cajaForm.controls['cashdescription'];
  }
  get active() {
    return this.cajaForm.controls['active'];
  }

  assignTurnoToCaja(){
    if(this.selectedTurno && this.selectedCaja){
      this.turnosService.assignTurnoToCaja(this.selectedTurno.turnid, this.selectedCaja.cashid).subscribe({
        next: () => {
          this.msgService.add({ severity: 'success', summary: 'Success', detail: 'Turno asignado a caja exitosamente' });
        },
        error: (error) => {
          this.msgService.add({ severity: 'error', summary: 'Error', detail: ` ${error.error} ` })
        },
      });
    }
  }



  submitTurno() {
    const data = this.turnoForm.value;
    this.turnosService.createTurno(data as ITurnrRegister).subscribe({
      next: () => {
        this.msgService.add({ severity: 'success', summary: 'Success', detail: 'Turno creado exitosamente' });
        this.turnoForm.reset();
        this.loadTurns();
      },
      error: () => this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al crear el turno' }),
    });
  }

  submitCaja() {
    const data = this.cajaForm.value;
    this.cajaService.createCaja(data as IRegisterCash).subscribe({
      next: () => {
        this.msgService.add({ severity: 'success', summary: 'Success', detail: 'Caja creada exitosamente' });
        this.cajaForm.reset();
        this.loadCajas();
      },
      error: () => this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al crear la caja' }),
    });
  }
  ngOnInit() {
    this.loadTurns();
    this.loadCajas();
  }
  loadTurns() { 
    this.isLoading = true;
    this.turnosService.getTurnos().subscribe(
      {
        next: (data) => {
          this.turnos = data.map(turno => {

            return {...turno, label: `${turno.description} - ${turno.date}`};
          });
        },
        error: () =>{ 
          this.msgService.add({ severity: 'error', summary: 'Error', detail: "Error al cargar los roles"})
          this.isLoading = false
        }  ,
        complete: () => this.isLoading = false,  
      }
    );
  }
  loadCajas() {
    this.isLoading = true;
    this.cajaService.getCajas().subscribe(
      {
        next: (data) => {
          this.cajas = data.map(caja => {
            return {...caja, label: `${caja.cashdescription}`};
          });
        },
        error: () =>{ 
          this.msgService.add({ severity: 'error', summary: 'Error', detail: "Error al cargar las cajas"})
          this.isLoading = false
        }  ,
        complete: () => this.isLoading = false,  
      }
    );
  }

  deleteTurno(){

    if(this.selectedTurno){
      this.turnosService.deleteTurno(this.selectedTurno.turnid).subscribe({
        next: () => {
          this.msgService.add({ severity: 'success', summary: 'Success', detail: 'Turno eliminado exitosamente' });
          this.loadTurns();
        },
        error: () => this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al eliminar el turno' }),
      });
    }
  }
  
  deleteCaja(){
    if(this.selectedCaja){
      this.cajaService.deleteCaja(this.selectedCaja.cashid).subscribe({
        next: () => {
          this.msgService.add({ severity: 'success', summary: 'Success', detail: 'Caja eliminada exitosamente' });
          this.loadCajas();
        },
        error: () => this.msgService.add({ severity: 'error', summary: 'Error', detail: 'Error al eliminar la caja' }),
      });
    }
  }


  onTurnoChange(event: any) {
    console.log(event.value);
    this.selectedTurno = event.value;
  }

  onCajaChange(event: any) {
    console.log(event.value);
    this.selectedCaja = event.value;
  }
  navigateToTurno() {
    document.getElementById('agregarTurno')?.scrollIntoView({ behavior: 'smooth' });
  }
  navigateToCaja() {
    document.getElementById('agregarCaja')?.scrollIntoView({ behavior: 'smooth' });
  }
}
function descriptionValidator(control: FormControl) {
  const value = control.value;
  const regex = /^[A-Z]{2}\d{4}/; // Elimina el símbolo $ del final
  if (!regex.test(value)) {
    return { invalidDescription: true };
  }
  return null;
}