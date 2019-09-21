import { IPublisher } from 'app/shared/model/publisher.model';
import { IAuthor } from 'app/shared/model/author.model';
import { ICategory } from 'app/shared/model/category.model';
import { IOrder } from 'app/shared/model/order.model';

export interface IBook {
  id?: number;
  iSBN?: string;
  title?: string;
  price?: number;
  numberOfPages?: number;
  publishYear?: string;
  coverUrl?: string;
  publisher?: IPublisher;
  authors?: IAuthor[];
  categories?: ICategory[];
  order?: IOrder;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public iSBN?: string,
    public title?: string,
    public price?: number,
    public numberOfPages?: number,
    public publishYear?: string,
    public coverUrl?: string,
    public publisher?: IPublisher,
    public authors?: IAuthor[],
    public categories?: ICategory[],
    public order?: IOrder
  ) {}
}
