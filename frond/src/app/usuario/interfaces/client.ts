export interface IClient {
  clientid: number;
  name: string;
  lastname: string;
  identification: string;
  email: string;
  phonenumber: string;
  address: string;
  referenceaddress: string;
}

export interface IClientRegister {

    name: string;
    lastname: string;
    identification: string;
    email: string;
    phonenumber: string;
    address: string;
    referenceaddress: string;
  }
  
    export interface IClientUpdate {
        name: string;
        lastname: string;
        email: string;
        phonenumber: string;
        address: string;
        referenceaddress: string;
    }