import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimeNgModule } from '../prime-ng/prime-ng.module';
import { LoadingScreamComponent } from './pages/loading-scream/loading-scream.component';
import { HeaderComponent } from './pages/header/header.component';
import { SliderMenuComponent } from './pages/slider-menu/slider-menu.component';
import { UsuarioRoutingModule } from '../usuario/usuario-routing.module';

@NgModule({
  declarations: [
    LoadingScreamComponent,
    HeaderComponent,
    SliderMenuComponent
  ],
  imports: [
    CommonModule,
    PrimeNgModule,
    UsuarioRoutingModule,
  ],
  exports: [
    LoadingScreamComponent,
    HeaderComponent,
    SliderMenuComponent
  ]
})
export class SharedModule { }