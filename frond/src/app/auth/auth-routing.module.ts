import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { LayoutPageComponent } from './pages/layout-page/layout-page.component';
import { RegisterComponent } from './pages/register-page/register.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutPageComponent,
    children: [
      { path: 'login', component: LoginPageComponent } , { path: 'register', component: RegisterComponent}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)], // Cambia forRoot por forChild
  exports: [RouterModule]
})
export class AuthRoutingModule { }