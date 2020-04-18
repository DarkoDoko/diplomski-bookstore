import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BookComponentsPage, BookDeleteDialog, BookUpdatePage } from './book.page-object';

const expect = chai.expect;

describe('Book e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bookComponentsPage: BookComponentsPage;
  let bookUpdatePage: BookUpdatePage;
  let bookDeleteDialog: BookDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Books', async () => {
    await navBarPage.goToEntity('book');
    bookComponentsPage = new BookComponentsPage();
    await browser.wait(ec.visibilityOf(bookComponentsPage.title), 5000);
    expect(await bookComponentsPage.getTitle()).to.eq('Books');
    await browser.wait(ec.or(ec.visibilityOf(bookComponentsPage.entities), ec.visibilityOf(bookComponentsPage.noResult)), 1000);
  });

  it('should load create Book page', async () => {
    await bookComponentsPage.clickOnCreateButton();
    bookUpdatePage = new BookUpdatePage();
    expect(await bookUpdatePage.getPageTitle()).to.eq('Create or edit a Book');
    await bookUpdatePage.cancel();
  });

  it('should create and save Books', async () => {
    const nbButtonsBeforeCreate = await bookComponentsPage.countDeleteButtons();

    await bookComponentsPage.clickOnCreateButton();

    await promise.all([
      bookUpdatePage.setISBNInput('iSBN'),
      bookUpdatePage.setTitleInput('title'),
      bookUpdatePage.setPriceInput('5'),
      bookUpdatePage.setNumberOfPagesInput('5'),
      bookUpdatePage.setPublishYearInput('publishYear'),
      bookUpdatePage.setCoverUrlInput('coverUrl'),
      bookUpdatePage.publisherSelectLastOption(),
      // bookUpdatePage.authorSelectLastOption(),
      bookUpdatePage.categorySelectLastOption()
    ]);

    expect(await bookUpdatePage.getISBNInput()).to.eq('iSBN', 'Expected ISBN value to be equals to iSBN');
    expect(await bookUpdatePage.getTitleInput()).to.eq('title', 'Expected Title value to be equals to title');
    expect(await bookUpdatePage.getPriceInput()).to.eq('5', 'Expected price value to be equals to 5');
    expect(await bookUpdatePage.getNumberOfPagesInput()).to.eq('5', 'Expected numberOfPages value to be equals to 5');
    expect(await bookUpdatePage.getPublishYearInput()).to.eq('publishYear', 'Expected PublishYear value to be equals to publishYear');
    expect(await bookUpdatePage.getCoverUrlInput()).to.eq('coverUrl', 'Expected CoverUrl value to be equals to coverUrl');

    await bookUpdatePage.save();
    expect(await bookUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bookComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Book', async () => {
    const nbButtonsBeforeDelete = await bookComponentsPage.countDeleteButtons();
    await bookComponentsPage.clickOnLastDeleteButton();

    bookDeleteDialog = new BookDeleteDialog();
    expect(await bookDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Book?');
    await bookDeleteDialog.clickOnConfirmButton();

    expect(await bookComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
