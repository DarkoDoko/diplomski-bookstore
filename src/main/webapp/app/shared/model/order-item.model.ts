import { IBook } from 'app/shared/model/book.model';
import { IOrder } from 'app/shared/model/order.model';

export interface IOrderItem {
  id?: number;
  quantity?: number;
  totalPrice?: number;
  book?: IBook;
  order?: IOrder;
}

export class OrderItem implements IOrderItem {
  constructor(public id?: number, public quantity?: number, public totalPrice?: number, public book?: IBook, public order?: IOrder) {}
}
