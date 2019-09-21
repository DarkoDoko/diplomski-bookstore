import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class BookComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-book div table .btn-danger'));
  title = element.all(by.css('jhi-book div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
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

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setISBNInput(iSBN) {
    await this.iSBNInput.sendKeys(iSBN);
  }

  async getISBNInput() {
    return await this.iSBNInput.getAttribute('value');
  }

  async setTitleInput(title) {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput() {
    return await this.titleInput.getAttribute('value');
  }

  async setPriceInput(price) {
    await this.priceInput.sendKeys(price);
  }

  async getPriceInput() {
    return await this.priceInput.getAttribute('value');
  }

  async setNumberOfPagesInput(numberOfPages) {
    await this.numberOfPagesInput.sendKeys(numberOfPages);
  }

  async getNumberOfPagesInput() {
    return await this.numberOfPagesInput.getAttribute('value');
  }

  async setPublishYearInput(publishYear) {
    await this.publishYearInput.sendKeys(publishYear);
  }

  async getPublishYearInput() {
    return await this.publishYearInput.getAttribute('value');
  }

  async setCoverUrlInput(coverUrl) {
    await this.coverUrlInput.sendKeys(coverUrl);
  }

  async getCoverUrlInput() {
    return await this.coverUrlInput.getAttribute('value');
  }

  async publisherSelectLastOption(timeout?: number) {
    await this.publisherSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async publisherSelectOption(option) {
    await this.publisherSelect.sendKeys(option);
  }

  getPublisherSelect(): ElementFinder {
    return this.publisherSelect;
  }

  async getPublisherSelectedOption() {
    return await this.publisherSelect.element(by.css('option:checked')).getText();
  }

  async authorSelectLastOption(timeout?: number) {
    await this.authorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async authorSelectOption(option) {
    await this.authorSelect.sendKeys(option);
  }

  getAuthorSelect(): ElementFinder {
    return this.authorSelect;
  }

  async getAuthorSelectedOption() {
    return await this.authorSelect.element(by.css('option:checked')).getText();
  }

  async categorySelectLastOption(timeout?: number) {
    await this.categorySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async categorySelectOption(option) {
    await this.categorySelect.sendKeys(option);
  }

  getCategorySelect(): ElementFinder {
    return this.categorySelect;
  }

  async getCategorySelectedOption() {
    return await this.categorySelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class BookDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-book-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-book'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
