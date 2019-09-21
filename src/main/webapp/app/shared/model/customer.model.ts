import { IUser } from 'app/core/user/user.model';
import { IAddress } from 'app/shared/model/address.model';
import { IOrder } from 'app/shared/model/order.model';

export interface ICustomer {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  telephone?: string;
  user?: IUser;
  addresses?: IAddress[];
  orders?: IOrder[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public telephone?: string,
    public user?: IUser,
    public addresses?: IAddress[],
    public orders?: IOrder[]
  ) {}
}
