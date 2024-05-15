
export interface Usuario {
  userid: number;
  username: string;
  email: string;
  password: string;
  creationdate: string;
  usercreate: number;
  userapproval: number;
  dateapproval: null | string;
  rol_rolid: number;
  status_statusid: number;
}



export interface UserLogin {
    usuario: string;
    password: string;
}


export interface UsuarioRegister {
  username: string;
  email: string;
  password: string;
  rol_rolid: number;
  status_statusid: number;
}


