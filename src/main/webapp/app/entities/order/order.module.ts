import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookstoreSharedModule } from 'app/shared';
import {
  OrderComponent,
  OrderDetailComponent,
  OrderUpdateComponent,
  OrderDeletePopupComponent,
  OrderDeleteDialogComponent,
  orderRoute,
  orderPopupRoute
} from './';

const ENTITY_STATES = [...orderRoute, ...orderPopupRoute];

@NgModule({
  imports: [BookstoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [OrderComponent, OrderDetailComponent, OrderUpdateComponent, OrderDeleteDialogComponent, OrderDeletePopupComponent],
  entryComponents: [OrderComponent, OrderUpdateComponent, OrderDeleteDialogComponent, OrderDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookstoreOrderModule {}
