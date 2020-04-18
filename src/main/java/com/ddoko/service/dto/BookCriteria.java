package com.ddoko.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link com.ddoko.domain.Book} entity. This class is used
 * in {@link com.ddoko.web.rest.BookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter iSBN;

    private StringFilter title;

    private BigDecimalFilter price;

    private IntegerFilter numberOfPages;

    private StringFilter publishYear;

    private StringFilter coverUrl;

    private LongFilter publisherId;

    private LongFilter authorId;

    private LongFilter categoryId;

    public BookCriteria() {
    }

    public BookCriteria(BookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.iSBN = other.iSBN == null ? null : other.iSBN.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.numberOfPages = other.numberOfPages == null ? null : other.numberOfPages.copy();
        this.publishYear = other.publishYear == null ? null : other.publishYear.copy();
        this.coverUrl = other.coverUrl == null ? null : other.coverUrl.copy();
        this.publisherId = other.publisherId == null ? null : other.publisherId.copy();
        this.authorId = other.authorId == null ? null : other.authorId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
    }

    @Override
    public BookCriteria copy() {
        return new BookCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getiSBN() {
        return iSBN;
    }

    public void setiSBN(StringFilter iSBN) {
        this.iSBN = iSBN;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public IntegerFilter getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(IntegerFilter numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public StringFilter getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(StringFilter publishYear) {
        this.publishYear = publishYear;
    }

    public StringFilter getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(StringFilter coverUrl) {
        this.coverUrl = coverUrl;
    }

    public LongFilter getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(LongFilter publisherId) {
        this.publisherId = publisherId;
    }

    public LongFilter getAuthorId() {
        return authorId;
    }

    public void setAuthorId(LongFilter authorId) {
        this.authorId = authorId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookCriteria that = (BookCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(iSBN, that.iSBN) &&
            Objects.equals(title, that.title) &&
            Objects.equals(price, that.price) &&
            Objects.equals(numberOfPages, that.numberOfPages) &&
            Objects.equals(publishYear, that.publishYear) &&
            Objects.equals(coverUrl, that.coverUrl) &&
            Objects.equals(publisherId, that.publisherId) &&
            Objects.equals(authorId, that.authorId) &&
            Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        iSBN,
        title,
        price,
        numberOfPages,
        publishYear,
        coverUrl,
        publisherId,
        authorId,
        categoryId
        );
    }

    @Override
    public String toString() {
        return "BookCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (iSBN != null ? "iSBN=" + iSBN + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (numberOfPages != null ? "numberOfPages=" + numberOfPages + ", " : "") +
                (publishYear != null ? "publishYear=" + publishYear + ", " : "") +
                (coverUrl != null ? "coverUrl=" + coverUrl + ", " : "") +
                (publisherId != null ? "publisherId=" + publisherId + ", " : "") +
                (authorId != null ? "authorId=" + authorId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
