import { IAddress } from 'app/shared/model/address.model';
import { IOrder } from 'app/shared/model/order.model';

export interface ICustomer {
  id?: number;
  addresses?: IAddress[];
  orders?: IOrder[];
}

export class Customer implements ICustomer {
  constructor(public id?: number, public addresses?: IAddress[], public orders?: IOrder[]) {}
}
