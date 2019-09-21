import { IBook } from 'app/shared/model/book.model';

export interface ICategory {
  id?: number;
  name?: string;
  books?: IBook[];
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public books?: IBook[]) {}
}
