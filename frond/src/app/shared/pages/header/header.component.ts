import { Component } from '@angular/core';
import { AuthService } from '../../../auth/services/auth.service';
import { Router } from '@angular/router';
import { ItemMenuService } from '../../services/item-menu.service';
import { Usuario } from '../../../auth/interfaces/usuario';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent {
  visible: boolean = false;

  constructor(private layoutService: ItemMenuService, private authService: AuthService, 
    private router: Router
  ) { }
  get usuario(): Usuario | undefined {  
    return this.authService.currentUser;
  }
  ngOnInit() {
    this.layoutService.visible$.subscribe(visible => this.visible = visible);
  }

  openSidebar() {
    this.layoutService.toggleVisible();
  }
  
  onLogout() {
    this.authService.logout().subscribe(
      {
        error: error => {
          console.error(error);
        },
        complete: () => {
          this.router.navigate(['/auth/login']);
        }
      }
    );

  }
}
