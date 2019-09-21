import { Moment } from 'moment';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IOrder {
  id?: number;
  placedAt?: Moment;
  code?: string;
  orderItems?: IOrderItem[];
  customer?: ICustomer;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public placedAt?: Moment,
    public code?: string,
    public orderItems?: IOrderItem[],
    public customer?: ICustomer
  ) {}
}
