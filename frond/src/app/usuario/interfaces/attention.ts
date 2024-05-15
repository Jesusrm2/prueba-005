export interface IListAtention {
  attention: Attention;
  user: User;
}

interface User {
  userid: number;
  username: string;
  email: string;
  password: string;
  creationdate: string;
  usercreate: number;
  userapproval: number;
  dateapproval: string;
  rol_rolid: Rolrolid;
  status_statusid: Statusstatusid;
  usercash: Cashid[];
}

interface Statusstatusid {
  statusid: number;
  description: string;
}

interface Rolrolid {
  rolid: number;
  rolname: string;
}

interface Attention {
  attentionid: number;
  turn: Turn;
  client: Client;
  attentionType: null;
  attentionStatus: null;
}

interface Client {
  clientid: number;
  name: string;
  lastname: string;
  identification: string;
  email: string;
  phonenumber: string;
  address: string;
  referenceaddress: string;
}

interface Turn {
  turnid: number;
  description: string;
  date: string;
  usergestorid: number;
  cashid: Cashid;
}

interface Cashid {
  cashid: number;
  cashdescription: string;
  active: string;
}