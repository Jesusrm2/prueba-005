export interface ITurn {
  turnid: number;
  description: string;
  date: string;
  usergestorid: number;
  cash_cashid: null;
}

export interface ITurnrRegister {

    description: string;
    date: string;
  }

export interface ITurnsCashs {
  turnid: number;
  description: string;
  date: string;
  usergestorid: number;
  cashid: Cashid | null;
}

interface Cashid {
  cashid: number;
  cashdescription: string;
  active: string;
}