import { element, by, ElementFinder } from 'protractor';

export class BookComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-book div table .btn-danger'));
  title = element.all(by.css('jhi-book div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class BookUpdatePage {
  pageTitle = element(by.id('jhi-book-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  iSBNInput = element(by.id('field_iSBN'));
  titleInput = element(by.id('field_title'));
  priceInput = element(by.id('field_price'));
  numberOfPagesInput = element(by.id('field_numberOfPages'));
  publishYearInput = element(by.id('field_publishYear'));
  coverUrlInput = element(by.id('field_coverUrl'));

  publisherSelect = element(by.id('field_publisher'));
  authorSelect = element(by.id('field_author'));
  categorySelect = element(by.id('field_category'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setISBNInput(iSBN: string): Promise<void> {
    await this.iSBNInput.sendKeys(iSBN);
  }

  async getISBNInput(): Promise<string> {
    return await this.iSBNInput.getAttribute('value');
  }

  async setTitleInput(title: string): Promise<void> {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput(): Promise<string> {
    return await this.titleInput.getAttribute('value');
  }

  async setPriceInput(price: string): Promise<void> {
    await this.priceInput.sendKeys(price);
  }

  async getPriceInput(): Promise<string> {
    return await this.priceInput.getAttribute('value');
  }

  async setNumberOfPagesInput(numberOfPages: string): Promise<void> {
    await this.numberOfPagesInput.sendKeys(numberOfPages);
  }

  async getNumberOfPagesInput(): Promise<string> {
    return await this.numberOfPagesInput.getAttribute('value');
  }

  async setPublishYearInput(publishYear: string): Promise<void> {
    await this.publishYearInput.sendKeys(publishYear);
  }

  async getPublishYearInput(): Promise<string> {
    return await this.publishYearInput.getAttribute('value');
  }

  async setCoverUrlInput(coverUrl: string): Promise<void> {
    await this.coverUrlInput.sendKeys(coverUrl);
  }

  async getCoverUrlInput(): Promise<string> {
    return await this.coverUrlInput.getAttribute('value');
  }

  async publisherSelectLastOption(): Promise<void> {
    await this.publisherSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async publisherSelectOption(option: string): Promise<void> {
    await this.publisherSelect.sendKeys(option);
  }

  getPublisherSelect(): ElementFinder {
    return this.publisherSelect;
  }

  async getPublisherSelectedOption(): Promise<string> {
    return await this.publisherSelect.element(by.css('option:checked')).getText();
  }

  async authorSelectLastOption(): Promise<void> {
    await this.authorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async authorSelectOption(option: string): Promise<void> {
    await this.authorSelect.sendKeys(option);
  }

  getAuthorSelect(): ElementFinder {
    return this.authorSelect;
  }

  async getAuthorSelectedOption(): Promise<string> {
    return await this.authorSelect.element(by.css('option:checked')).getText();
  }

  async categorySelectLastOption(): Promise<void> {
    await this.categorySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async categorySelectOption(option: string): Promise<void> {
    await this.categorySelect.sendKeys(option);
  }

  getCategorySelect(): ElementFinder {
    return this.categorySelect;
  }

  async getCategorySelectedOption(): Promise<string> {
    return await this.categorySelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class BookDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-book-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-book'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
