import { IPublisher } from 'app/shared/model/publisher.model';
import { IAuthor } from 'app/shared/model/author.model';
import { ICategory } from 'app/shared/model/category.model';

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
  category?: ICategory;
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
    public category?: ICategory
  ) {}
}
