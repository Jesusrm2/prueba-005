import { Component } from '@angular/core';
import { AdminUsuariosService } from '../../services/admin-usuarios.service';
import { MessageService } from 'primeng/api';
import { Usuario } from '../../../auth/interfaces/usuario';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-usuario',
  templateUrl: './admin-usuario.component.html'
})
export class AdminUsuarioComponent {
  listUsers: Usuario[] = [];
  isLoading = false;
  roles = [{id: 1, name: 'Administrador'}, 
  {id: 2, name: 'Gestor'}, 
  {id: 3, name: 'Cajero'}, 
  {id: 4, name: 'Cliente'}]; 

  constructor(
    private adminUsuariosService: AdminUsuariosService,
    private msgService: MessageService,
    private router: Router,
  ) {   
}
ngOnInit() {
  this.loadRoles();
}
getRoleName(rolid: number): string {
  const role = this.roles.find(role => role.id == rolid);
  return role ? role.name : 'Rol no encontrado';
}
formUser( user?: Usuario) {
  if (user) {
    this.adminUsuariosService.selectUserForm(user);
    this.router.navigate(['/form-usuario']);
  }else{
    this.router.navigate(['/form-usuario']);
  }
 
}
loadRoles() {
  this.isLoading = true;
  this.adminUsuariosService.getUsuarios().subscribe(
    {
      next: (usuarios) => {
        this.listUsers = usuarios;
      },
      error: () =>{ 
        
        this.msgService.add({ severity: 'error', summary: 'Error', detail: "Error al cargar los usuarios"})
       this.isLoading = false
      }  ,
      complete: () => this.isLoading = false,  
    }
  );
}
deleteUser(user: Usuario) {
  this.isLoading = true;
  this.adminUsuariosService.deleteUsuario(user.userid).subscribe({
    next: () => {
      this.msgService.add({ severity: 'success', summary: 'Éxito', detail: 'Usuario eliminado correctamente' });
      this.loadRoles();
    },
    error: () => {
      this.isLoading = false;
      this.loadRoles();
    },
    complete: () => {
      this.loadRoles();
      this.isLoading = false;
    }
  });
}

}
