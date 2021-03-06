import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.BookstoreCustomerModule)
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.BookstoreAddressModule)
      },
      {
        path: 'book',
        loadChildren: () => import('./book/book.module').then(m => m.BookstoreBookModule)
      },
      {
        path: 'author',
        loadChildren: () => import('./author/author.module').then(m => m.BookstoreAuthorModule)
      },
      {
        path: 'publisher',
        loadChildren: () => import('./publisher/publisher.module').then(m => m.BookstorePublisherModule)
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.BookstoreCategoryModule)
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.BookstoreOrderModule)
      },
      {
        path: 'order-item',
        loadChildren: () => import('./order-item/order-item.module').then(m => m.BookstoreOrderItemModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BookstoreEntityModule {}
