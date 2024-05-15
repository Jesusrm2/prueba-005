import { Component } from '@angular/core';
import { AuthService } from '../../../auth/services/auth.service';
import { Router } from '@angular/router';
import { ItemMenuService } from '../../../shared/services/item-menu.service';

@Component({
  selector: 'app-layout-page',
  templateUrl: './layout-page.component.html',
  styleUrl: './layout-page.component.css'
})
export class LayoutPageComponent {
  visible = false;
  constructor(
    private authService: AuthService,
    private router: Router,
    private itemMenuService: ItemMenuService,
  ) {}
  ngOnInit() {
    this.itemMenuService.visible$.subscribe(visible => this.visible = visible);
  }
 


}
