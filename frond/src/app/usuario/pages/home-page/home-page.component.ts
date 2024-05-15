import { Component } from '@angular/core';
import { Usuario } from '../../../auth/interfaces/usuario';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html'
})
export class HomePageComponent {
  constructor(
    private authService: AuthService,
  ) {}
  ngOnInit(){
  }
  get user():Usuario | undefined {
    return this.authService.currentUser;
  }
}
