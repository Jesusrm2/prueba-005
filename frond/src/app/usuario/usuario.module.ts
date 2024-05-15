import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimeNgModule } from '../prime-ng/prime-ng.module';
import { ReactiveFormsModule } from '@angular/forms';
import { LayoutPageComponent } from './pages/layout-page/layout-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { FormUsuarioComponent } from './pages/form-usuario/form-usuario.component';

import { UsuarioRoutingModule } from './usuario-routing.module';
import { SharedModule } from '../shared/shared.module';

import { AuthModule } from '../auth/auth.module';
import { AdminUsuarioComponent } from './pages/admin-usuario/admin-usuario.component';
import { AsignarTurnoComponent } from './pages/asignar-turno/asignar-turno.component';
import { AsignarCajaComponent } from './pages/asignar-caja/asignar-caja.component';
import { AdminClientsComponent } from './pages/admin-clients/admin-clients.component';
import { FormClientsComponent } from './pages/form-clients/form-clients.component';
import { AdminCajaComponent } from './pages/admin-caja/admin-caja.component';
import { CambioServicioComponent } from './components/cambio-servicio/cambio-servicio.component';
import { CambioMetodoPagoComponent } from './components/cambio-metodo-pago/cambio-metodo-pago.component';
import { CancelarContratoComponent } from './components/cancelar-contrato/cancelar-contrato.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';


@NgModule({
  declarations: [
    LayoutPageComponent,
    HomePageComponent,
    FormUsuarioComponent,
    AdminUsuarioComponent,
    AsignarTurnoComponent,
    AsignarCajaComponent,
    AdminClientsComponent,
    FormClientsComponent,
    AdminCajaComponent,
    CambioServicioComponent,
    CambioMetodoPagoComponent,
    CancelarContratoComponent,
    DashboardComponent

  ],
  imports: [
    CommonModule,
    PrimeNgModule,
    UsuarioRoutingModule,
    ReactiveFormsModule,
    SharedModule,
    AuthModule
  ]

})
export class UsuarioModule { }
