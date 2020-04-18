import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html'
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    placedAt: [null, [Validators.required]],
    code: [null, [Validators.required]],
    customer: [null, Validators.required]
  });

  constructor(
    protected orderService: OrderService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      if (!order.id) {
        const today = moment().startOf('day');
        order.placedAt = today;
      }

      this.updateForm(order);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      placedAt: order.placedAt ? order.placedAt.format(DATE_TIME_FORMAT) : null,
      code: order.code,
      customer: order.customer
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      placedAt: this.editForm.get(['placedAt'])!.value ? moment(this.editForm.get(['placedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      code: this.editForm.get(['code'])!.value,
      customer: this.editForm.get(['customer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
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

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
