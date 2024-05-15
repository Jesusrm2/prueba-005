export interface IContract {
  devices?: number[];
  serviceid: number;
  methodpaymentid: number;
  statuscontractid: number;
  clientid: number;
  startdate: string;
  enddate: string;
}

export interface IContractRegister {
  startdate: string;
  enddate: string;
  serviceid: number;
  methodpaymentid: number;
  clientid: number;
  devices: number[];
}


export interface IListContracts {
  contractid: number;
  startdate: string;
  enddate: string;
  client_clientid: Clientclientid;
  service_serviceid: Serviceserviceid;
  statuscontract_statuscontractid: Statuscontractstatuscontractid;
  methodpayment_methodpaymentid: Methodpaymentmethodpaymentid;
}

interface Methodpaymentmethodpaymentid {
  methodpaymentid: number;
  description: string;
}

interface Statuscontractstatuscontractid {
  statuscontractid: number;
  description: string;
}

interface Serviceserviceid {
  serviceid: number;
  servicename: string;
  servicedescription: string;
  price: string;
}

interface Clientclientid {
  clientid: number;
  name: string;
  lastname: string;
  identification: string;
  email: string;
  phonenumber: string;
  address: string;
  referenceaddress: string;
}