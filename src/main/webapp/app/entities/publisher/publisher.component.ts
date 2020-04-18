import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPublisher } from 'app/shared/model/publisher.model';
import { AccountService } from 'app/core';
import { PublisherService } from './publisher.service';

@Component({
  selector: 'jhi-publisher',
  templateUrl: './publisher.component.html'
})
export class PublisherComponent implements OnInit, OnDestroy {
  publishers: IPublisher[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected publisherService: PublisherService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.publisherService
      .query()
      .pipe(
        filter((res: HttpResponse<IPublisher[]>) => res.ok),
        map((res: HttpResponse<IPublisher[]>) => res.body)
      )
      .subscribe(
        (res: IPublisher[]) => {
          this.publishers = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPublishers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPublisher) {
    return item.id;
  }

  registerChangeInPublishers() {
    this.eventSubscriber = this.eventManager.subscribe('publisherListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
