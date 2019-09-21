import { IBook } from 'app/shared/model/book.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IOrder {
  id?: number;
  books?: IBook[];
  customer?: ICustomer;
}

export class Order implements IOrder {
  constructor(public id?: number, public books?: IBook[], public customer?: ICustomer) {}
}
