import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBook, Book } from 'app/shared/model/book.model';
import { BookService } from './book.service';
import { IPublisher } from 'app/shared/model/publisher.model';
import { PublisherService } from 'app/entities/publisher';
import { IAuthor } from 'app/shared/model/author.model';
import { AuthorService } from 'app/entities/author';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';

@Component({
  selector: 'jhi-book-update',
  templateUrl: './book-update.component.html'
})
export class BookUpdateComponent implements OnInit {
  isSaving: boolean;

  publishers: IPublisher[];

  authors: IAuthor[];

  categories: ICategory[];

  editForm = this.fb.group({
    id: [],
    iSBN: [null, [Validators.required]],
    title: [null, [Validators.required]],
    price: [null, [Validators.required, Validators.min(0)]],
    numberOfPages: [],
    publishYear: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(4)]],
    coverUrl: [],
    publisher: [],
    authors: [],
    category: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected bookService: BookService,
    protected publisherService: PublisherService,
    protected authorService: AuthorService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ book }) => {
      this.updateForm(book);
    });
    this.publisherService
      .query({ 'bookId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPublisher[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPublisher[]>) => response.body)
      )
      .subscribe(
        (res: IPublisher[]) => {
          if (!this.editForm.get('publisher').value || !this.editForm.get('publisher').value.id) {
            this.publishers = res;
          } else {
            this.publisherService
              .find(this.editForm.get('publisher').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPublisher>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPublisher>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPublisher) => (this.publishers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.authorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAuthor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAuthor[]>) => response.body)
      )
      .subscribe((res: IAuthor[]) => (this.authors = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.categoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategory[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategory[]>) => response.body)
      )
      .subscribe((res: ICategory[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(book: IBook) {
    this.editForm.patchValue({
      id: book.id,
      iSBN: book.iSBN,
      title: book.title,
      price: book.price,
      numberOfPages: book.numberOfPages,
      publishYear: book.publishYear,
      coverUrl: book.coverUrl,
      publisher: book.publisher,
      authors: book.authors,
      category: book.category
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const book = this.createFromForm();
    if (book.id !== undefined) {
      this.subscribeToSaveResponse(this.bookService.update(book));
    } else {
      this.subscribeToSaveResponse(this.bookService.create(book));
    }
  }

  private createFromForm(): IBook {
    return {
      ...new Book(),
      id: this.editForm.get(['id']).value,
      iSBN: this.editForm.get(['iSBN']).value,
      title: this.editForm.get(['title']).value,
      price: this.editForm.get(['price']).value,
      numberOfPages: this.editForm.get(['numberOfPages']).value,
      publishYear: this.editForm.get(['publishYear']).value,
      coverUrl: this.editForm.get(['coverUrl']).value,
      publisher: this.editForm.get(['publisher']).value,
      authors: this.editForm.get(['authors']).value,
      category: this.editForm.get(['category']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBook>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPublisherById(index: number, item: IPublisher) {
    return item.id;
  }

  trackAuthorById(index: number, item: IAuthor) {
    return item.id;
  }

  trackCategoryById(index: number, item: ICategory) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
