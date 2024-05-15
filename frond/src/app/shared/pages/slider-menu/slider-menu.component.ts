import { Component } from '@angular/core';
import { ItemMenuService } from '../../services/item-menu.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../auth/services/auth.service';
import { Usuario } from '../../../auth/interfaces/usuario';

@Component({
  selector: 'app-slider-menu',
  templateUrl: './slider-menu.component.html'
})
export class SliderMenuComponent {
  isLoading = false;
  visible: boolean = false;
  menuItems: MenuItem[] = [];
  usuario?: Usuario;
  constructor(public layoutService: ItemMenuService,
    private router: Router,
    private usuarioService: AuthService,
    private itemMenuService:ItemMenuService
  ) { }

  ngOnInit() {
    this.layoutService.visible$.subscribe(visible => this.visible = visible);
  
    this.itemMenuService.getUsuario().subscribe(
      {
        next: (usuario: Usuario) => {
          this.usuario = usuario;
          this.menuItems = this.generarMenuItems(this.usuario);
        },
        error: (err) => {
          console.log(err);
        }
      }
    );
  }

  closeSidebar() {
    console.log(this.usuario); 
    this.layoutService.toggleVisible();
  }

  navegar(ruta: string) {
    this.router.navigate([ruta]);
  }
  generarMenuItems(user?: Usuario): MenuItem[] {
    let items: MenuItem[] = [
      { label: 'Home', icon: 'pi pi-home', command: () => this.navegar('/home') }
    ];
  
    if (this.usuario) {
      switch (this.usuario.rol_rolid) {
        case 1: // Administradores
          items.push(
            { label: 'Dashboard', icon: 'pi pi-chart-bar', command: () => this.navegar('/dashboard') },
            { label: 'Administrar usuarios', icon: 'pi pi-user-plus', command: () => this.navegar('/admin-usuarios') }
          );
          break;
        case 2: // Gestores
          items.push(
            { label: 'Asignar turnos', icon: 'pi pi-calendar-plus', command: () => this.navegar('/asignar-turnos') },
            { label: 'Asignar cajas', icon: 'pi pi-cart-plus', command: () => this.navegar('/asignar-caja') },
            { label: 'Solicitar cajero', icon: 'pi pi-user-plus', command: () => this.navegar('/form-usuario') }
          );
          break;
        case 3: // Cajeros
          items.push(
            { label: 'Procesos de caja', icon: 'pi pi-wallet', command: () => this.navegar('/procesos-caja') },
            { label: 'Mantenimiento de clientes', icon: 'pi pi-users', command: () => this.navegar('/mantenimiento-clientes') }
          );
          break;
      }
    }
  
    return [
      {
        label: 'OPCIONES',
        items: items
      }
    ];
  }
}
interface MenuItem {
  label: string;
  icon?: string;
  items?: MenuItem[];
  command?: () => void;
}


