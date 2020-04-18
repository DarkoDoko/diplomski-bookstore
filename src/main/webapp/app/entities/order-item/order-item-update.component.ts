import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrderItem, OrderItem } from 'app/shared/model/order-item.model';
import { OrderItemService } from './order-item.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';

type SelectableEntity = IBook | IOrder;

@Component({
  selector: 'jhi-order-item-update',
  templateUrl: './order-item-update.component.html'
})
export class OrderItemUpdateComponent implements OnInit {
  isSaving = false;
  books: IBook[] = [];
  orders: IOrder[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required, Validators.min(0)]],
    totalPrice: [null, [Validators.required, Validators.min(0)]],
    book: [null, Validators.required],
    order: [null, Validators.required]
  });

  constructor(
    protected orderItemService: OrderItemService,
    protected bookService: BookService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderItem }) => {
      this.updateForm(orderItem);

      this.bookService.query().subscribe((res: HttpResponse<IBook[]>) => (this.books = res.body || []));

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));
    });
  }

  updateForm(orderItem: IOrderItem): void {
    this.editForm.patchValue({
      id: orderItem.id,
      quantity: orderItem.quantity,
      totalPrice: orderItem.totalPrice,
      book: orderItem.book,
      order: orderItem.order
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderItem = this.createFromForm();
    if (orderItem.id !== undefined) {
      this.subscribeToSaveResponse(this.orderItemService.update(orderItem));
    } else {
      this.subscribeToSaveResponse(this.orderItemService.create(orderItem));
    }
  }

  private createFromForm(): IOrderItem {
    return {
      ...new OrderItem(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      totalPrice: this.editForm.get(['totalPrice'])!.value,
      book: this.editForm.get(['book'])!.value,
      order: this.editForm.get(['order'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderItem>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
