import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LayoutPageComponent } from './pages/layout-page/layout-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { FormUsuarioComponent } from './pages/form-usuario/form-usuario.component';
import { AdminUsuarioComponent } from './pages/admin-usuario/admin-usuario.component';
import { AsignarTurnoComponent } from './pages/asignar-turno/asignar-turno.component';
import { AsignarCajaComponent } from './pages/asignar-caja/asignar-caja.component';
import { AdminClientsComponent } from './pages/admin-clients/admin-clients.component';
import { FormClientsComponent } from './pages/form-clients/form-clients.component';
import { AdminCajaComponent } from './pages/admin-caja/admin-caja.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';


const routes: Routes = [
  {
    path: '',
    component: LayoutPageComponent,
    children: [
      { path: 'home', component: HomePageComponent },
      { path: 'form-usuario', component: FormUsuarioComponent},
      { path: 'admin-usuarios', component: AdminUsuarioComponent},
      { path: 'asignar-turnos', component: AsignarTurnoComponent},
      { path: 'asignar-caja',component: AsignarCajaComponent },
      { path: 'mantenimiento-clientes',component: AdminClientsComponent  },
      { path: 'form-client', component: FormClientsComponent},
      { path: 'procesos-caja', component: AdminCajaComponent},
      { path: 'dashboard', component: DashboardComponent},
    ]
  }
   
];

@NgModule({
  imports: [RouterModule.forChild(routes)], 
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }