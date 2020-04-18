import { element, by, ElementFinder } from 'protractor';

export class AddressComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-address div table .btn-danger'));
  title = element.all(by.css('jhi-address div h2#page-heading span')).first();
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

export class AddressUpdatePage {
  pageTitle = element(by.id('jhi-address-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  address1Input = element(by.id('field_address1'));
  address2Input = element(by.id('field_address2'));
  cityInput = element(by.id('field_city'));
  postcodeInput = element(by.id('field_postcode'));
  countryInput = element(by.id('field_country'));

  customerSelect = element(by.id('field_customer'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setAddress1Input(address1: string): Promise<void> {
    await this.address1Input.sendKeys(address1);
  }

  async getAddress1Input(): Promise<string> {
    return await this.address1Input.getAttribute('value');
  }

  async setAddress2Input(address2: string): Promise<void> {
    await this.address2Input.sendKeys(address2);
  }

  async getAddress2Input(): Promise<string> {
    return await this.address2Input.getAttribute('value');
  }

  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async setPostcodeInput(postcode: string): Promise<void> {
    await this.postcodeInput.sendKeys(postcode);
  }

  async getPostcodeInput(): Promise<string> {
    return await this.postcodeInput.getAttribute('value');
  }

  async setCountryInput(country: string): Promise<void> {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput(): Promise<string> {
    return await this.countryInput.getAttribute('value');
  }

  async customerSelectLastOption(): Promise<void> {
    await this.customerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async customerSelectOption(option: string): Promise<void> {
    await this.customerSelect.sendKeys(option);
  }

  getCustomerSelect(): ElementFinder {
    return this.customerSelect;
  }

  async getCustomerSelectedOption(): Promise<string> {
    return await this.customerSelect.element(by.css('option:checked')).getText();
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

export class AddressDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-address-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-address'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
