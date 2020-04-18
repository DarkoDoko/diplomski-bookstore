import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BookstoreSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BookstoreSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [BookstoreSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookstoreSharedModule {
  static forRoot() {
    return {
      ngModule: BookstoreSharedModule
    };
  }
}
