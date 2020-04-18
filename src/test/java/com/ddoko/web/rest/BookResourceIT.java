package com.ddoko.web.rest;

import com.ddoko.BookstoreApp;
import com.ddoko.domain.Book;
import com.ddoko.domain.Publisher;
import com.ddoko.domain.Author;
import com.ddoko.domain.Category;
import com.ddoko.repository.BookRepository;
import com.ddoko.service.BookService;
import com.ddoko.service.dto.BookCriteria;
import com.ddoko.service.BookQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BookResource} REST controller.
 */
@SpringBootTest(classes = BookstoreApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BookResourceIT {

    private static final String DEFAULT_I_SBN = "AAAAAAAAAA";
    private static final String UPDATED_I_SBN = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(0 - 1);

    private static final Integer DEFAULT_NUMBER_OF_PAGES = 1;
    private static final Integer UPDATED_NUMBER_OF_PAGES = 2;
    private static final Integer SMALLER_NUMBER_OF_PAGES = 1 - 1;

    private static final String DEFAULT_PUBLISH_YEAR = "AAAA";
    private static final String UPDATED_PUBLISH_YEAR = "BBBB";

    private static final String DEFAULT_COVER_URL = "AAAAAAAAAA";
    private static final String UPDATED_COVER_URL = "BBBBBBBBBB";

    @Autowired
    private BookRepository bookRepository;

    @Mock
    private BookRepository bookRepositoryMock;

    @Mock
    private BookService bookServiceMock;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookQueryService bookQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookMockMvc;

    private Book book;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .iSBN(DEFAULT_I_SBN)
            .title(DEFAULT_TITLE)
            .price(DEFAULT_PRICE)
            .numberOfPages(DEFAULT_NUMBER_OF_PAGES)
            .publishYear(DEFAULT_PUBLISH_YEAR)
            .coverUrl(DEFAULT_COVER_URL);
        return book;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createUpdatedEntity(EntityManager em) {
        Book book = new Book()
            .iSBN(UPDATED_I_SBN)
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .numberOfPages(UPDATED_NUMBER_OF_PAGES)
            .publishYear(UPDATED_PUBLISH_YEAR)
            .coverUrl(UPDATED_COVER_URL);
        return book;
    }

    @BeforeEach
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book
        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getiSBN()).isEqualTo(DEFAULT_I_SBN);
        assertThat(testBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBook.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBook.getNumberOfPages()).isEqualTo(DEFAULT_NUMBER_OF_PAGES);
        assertThat(testBook.getPublishYear()).isEqualTo(DEFAULT_PUBLISH_YEAR);
        assertThat(testBook.getCoverUrl()).isEqualTo(DEFAULT_COVER_URL);
    }

    @Test
    @Transactional
    public void createBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book with an existing ID
        book.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkiSBNIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setiSBN(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setTitle(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setPrice(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublishYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setPublishYear(null);

        // Create the Book, which fails.

        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].iSBN").value(hasItem(DEFAULT_I_SBN)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].numberOfPages").value(hasItem(DEFAULT_NUMBER_OF_PAGES)))
            .andExpect(jsonPath("$.[*].publishYear").value(hasItem(DEFAULT_PUBLISH_YEAR)))
            .andExpect(jsonPath("$.[*].coverUrl").value(hasItem(DEFAULT_COVER_URL)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBooksWithEagerRelationshipsIsEnabled() throws Exception {
        when(bookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBookMockMvc.perform(get("/api/books?eagerload=true"))
            .andExpect(status().isOk());

        verify(bookServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBooksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(bookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBookMockMvc.perform(get("/api/books?eagerload=true"))
            .andExpect(status().isOk());

        verify(bookServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.iSBN").value(DEFAULT_I_SBN))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.numberOfPages").value(DEFAULT_NUMBER_OF_PAGES))
            .andExpect(jsonPath("$.publishYear").value(DEFAULT_PUBLISH_YEAR))
            .andExpect(jsonPath("$.coverUrl").value(DEFAULT_COVER_URL));
    }


    @Test
    @Transactional
    public void getBooksByIdFiltering() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        Long id = book.getId();

        defaultBookShouldBeFound("id.equals=" + id);
        defaultBookShouldNotBeFound("id.notEquals=" + id);

        defaultBookShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.greaterThan=" + id);

        defaultBookShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBooksByiSBNIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where iSBN equals to DEFAULT_I_SBN
        defaultBookShouldBeFound("iSBN.equals=" + DEFAULT_I_SBN);

        // Get all the bookList where iSBN equals to UPDATED_I_SBN
        defaultBookShouldNotBeFound("iSBN.equals=" + UPDATED_I_SBN);
    }

    @Test
    @Transactional
    public void getAllBooksByiSBNIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where iSBN not equals to DEFAULT_I_SBN
        defaultBookShouldNotBeFound("iSBN.notEquals=" + DEFAULT_I_SBN);

        // Get all the bookList where iSBN not equals to UPDATED_I_SBN
        defaultBookShouldBeFound("iSBN.notEquals=" + UPDATED_I_SBN);
    }

    @Test
    @Transactional
    public void getAllBooksByiSBNIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where iSBN in DEFAULT_I_SBN or UPDATED_I_SBN
        defaultBookShouldBeFound("iSBN.in=" + DEFAULT_I_SBN + "," + UPDATED_I_SBN);

        // Get all the bookList where iSBN equals to UPDATED_I_SBN
        defaultBookShouldNotBeFound("iSBN.in=" + UPDATED_I_SBN);
    }

    @Test
    @Transactional
    public void getAllBooksByiSBNIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where iSBN is not null
        defaultBookShouldBeFound("iSBN.specified=true");

        // Get all the bookList where iSBN is null
        defaultBookShouldNotBeFound("iSBN.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByiSBNContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where iSBN contains DEFAULT_I_SBN
        defaultBookShouldBeFound("iSBN.contains=" + DEFAULT_I_SBN);

        // Get all the bookList where iSBN contains UPDATED_I_SBN
        defaultBookShouldNotBeFound("iSBN.contains=" + UPDATED_I_SBN);
    }

    @Test
    @Transactional
    public void getAllBooksByiSBNNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where iSBN does not contain DEFAULT_I_SBN
        defaultBookShouldNotBeFound("iSBN.doesNotContain=" + DEFAULT_I_SBN);

        // Get all the bookList where iSBN does not contain UPDATED_I_SBN
        defaultBookShouldBeFound("iSBN.doesNotContain=" + UPDATED_I_SBN);
    }


    @Test
    @Transactional
    public void getAllBooksByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title equals to DEFAULT_TITLE
        defaultBookShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title not equals to DEFAULT_TITLE
        defaultBookShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the bookList where title not equals to UPDATED_TITLE
        defaultBookShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBookShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title is not null
        defaultBookShouldBeFound("title.specified=true");

        // Get all the bookList where title is null
        defaultBookShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByTitleContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title contains DEFAULT_TITLE
        defaultBookShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the bookList where title contains UPDATED_TITLE
        defaultBookShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title does not contain DEFAULT_TITLE
        defaultBookShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the bookList where title does not contain UPDATED_TITLE
        defaultBookShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllBooksByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price equals to DEFAULT_PRICE
        defaultBookShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the bookList where price equals to UPDATED_PRICE
        defaultBookShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price not equals to DEFAULT_PRICE
        defaultBookShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the bookList where price not equals to UPDATED_PRICE
        defaultBookShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultBookShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the bookList where price equals to UPDATED_PRICE
        defaultBookShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price is not null
        defaultBookShouldBeFound("price.specified=true");

        // Get all the bookList where price is null
        defaultBookShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price is greater than or equal to DEFAULT_PRICE
        defaultBookShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the bookList where price is greater than or equal to UPDATED_PRICE
        defaultBookShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price is less than or equal to DEFAULT_PRICE
        defaultBookShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the bookList where price is less than or equal to SMALLER_PRICE
        defaultBookShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price is less than DEFAULT_PRICE
        defaultBookShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the bookList where price is less than UPDATED_PRICE
        defaultBookShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllBooksByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where price is greater than DEFAULT_PRICE
        defaultBookShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the bookList where price is greater than SMALLER_PRICE
        defaultBookShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages equals to DEFAULT_NUMBER_OF_PAGES
        defaultBookShouldBeFound("numberOfPages.equals=" + DEFAULT_NUMBER_OF_PAGES);

        // Get all the bookList where numberOfPages equals to UPDATED_NUMBER_OF_PAGES
        defaultBookShouldNotBeFound("numberOfPages.equals=" + UPDATED_NUMBER_OF_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages not equals to DEFAULT_NUMBER_OF_PAGES
        defaultBookShouldNotBeFound("numberOfPages.notEquals=" + DEFAULT_NUMBER_OF_PAGES);

        // Get all the bookList where numberOfPages not equals to UPDATED_NUMBER_OF_PAGES
        defaultBookShouldBeFound("numberOfPages.notEquals=" + UPDATED_NUMBER_OF_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages in DEFAULT_NUMBER_OF_PAGES or UPDATED_NUMBER_OF_PAGES
        defaultBookShouldBeFound("numberOfPages.in=" + DEFAULT_NUMBER_OF_PAGES + "," + UPDATED_NUMBER_OF_PAGES);

        // Get all the bookList where numberOfPages equals to UPDATED_NUMBER_OF_PAGES
        defaultBookShouldNotBeFound("numberOfPages.in=" + UPDATED_NUMBER_OF_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages is not null
        defaultBookShouldBeFound("numberOfPages.specified=true");

        // Get all the bookList where numberOfPages is null
        defaultBookShouldNotBeFound("numberOfPages.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages is greater than or equal to DEFAULT_NUMBER_OF_PAGES
        defaultBookShouldBeFound("numberOfPages.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_PAGES);

        // Get all the bookList where numberOfPages is greater than or equal to UPDATED_NUMBER_OF_PAGES
        defaultBookShouldNotBeFound("numberOfPages.greaterThanOrEqual=" + UPDATED_NUMBER_OF_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages is less than or equal to DEFAULT_NUMBER_OF_PAGES
        defaultBookShouldBeFound("numberOfPages.lessThanOrEqual=" + DEFAULT_NUMBER_OF_PAGES);

        // Get all the bookList where numberOfPages is less than or equal to SMALLER_NUMBER_OF_PAGES
        defaultBookShouldNotBeFound("numberOfPages.lessThanOrEqual=" + SMALLER_NUMBER_OF_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsLessThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages is less than DEFAULT_NUMBER_OF_PAGES
        defaultBookShouldNotBeFound("numberOfPages.lessThan=" + DEFAULT_NUMBER_OF_PAGES);

        // Get all the bookList where numberOfPages is less than UPDATED_NUMBER_OF_PAGES
        defaultBookShouldBeFound("numberOfPages.lessThan=" + UPDATED_NUMBER_OF_PAGES);
    }

    @Test
    @Transactional
    public void getAllBooksByNumberOfPagesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where numberOfPages is greater than DEFAULT_NUMBER_OF_PAGES
        defaultBookShouldNotBeFound("numberOfPages.greaterThan=" + DEFAULT_NUMBER_OF_PAGES);

        // Get all the bookList where numberOfPages is greater than SMALLER_NUMBER_OF_PAGES
        defaultBookShouldBeFound("numberOfPages.greaterThan=" + SMALLER_NUMBER_OF_PAGES);
    }


    @Test
    @Transactional
    public void getAllBooksByPublishYearIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publishYear equals to DEFAULT_PUBLISH_YEAR
        defaultBookShouldBeFound("publishYear.equals=" + DEFAULT_PUBLISH_YEAR);

        // Get all the bookList where publishYear equals to UPDATED_PUBLISH_YEAR
        defaultBookShouldNotBeFound("publishYear.equals=" + UPDATED_PUBLISH_YEAR);
    }

    @Test
    @Transactional
    public void getAllBooksByPublishYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publishYear not equals to DEFAULT_PUBLISH_YEAR
        defaultBookShouldNotBeFound("publishYear.notEquals=" + DEFAULT_PUBLISH_YEAR);

        // Get all the bookList where publishYear not equals to UPDATED_PUBLISH_YEAR
        defaultBookShouldBeFound("publishYear.notEquals=" + UPDATED_PUBLISH_YEAR);
    }

    @Test
    @Transactional
    public void getAllBooksByPublishYearIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publishYear in DEFAULT_PUBLISH_YEAR or UPDATED_PUBLISH_YEAR
        defaultBookShouldBeFound("publishYear.in=" + DEFAULT_PUBLISH_YEAR + "," + UPDATED_PUBLISH_YEAR);

        // Get all the bookList where publishYear equals to UPDATED_PUBLISH_YEAR
        defaultBookShouldNotBeFound("publishYear.in=" + UPDATED_PUBLISH_YEAR);
    }

    @Test
    @Transactional
    public void getAllBooksByPublishYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publishYear is not null
        defaultBookShouldBeFound("publishYear.specified=true");

        // Get all the bookList where publishYear is null
        defaultBookShouldNotBeFound("publishYear.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByPublishYearContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publishYear contains DEFAULT_PUBLISH_YEAR
        defaultBookShouldBeFound("publishYear.contains=" + DEFAULT_PUBLISH_YEAR);

        // Get all the bookList where publishYear contains UPDATED_PUBLISH_YEAR
        defaultBookShouldNotBeFound("publishYear.contains=" + UPDATED_PUBLISH_YEAR);
    }

    @Test
    @Transactional
    public void getAllBooksByPublishYearNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publishYear does not contain DEFAULT_PUBLISH_YEAR
        defaultBookShouldNotBeFound("publishYear.doesNotContain=" + DEFAULT_PUBLISH_YEAR);

        // Get all the bookList where publishYear does not contain UPDATED_PUBLISH_YEAR
        defaultBookShouldBeFound("publishYear.doesNotContain=" + UPDATED_PUBLISH_YEAR);
    }


    @Test
    @Transactional
    public void getAllBooksByCoverUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where coverUrl equals to DEFAULT_COVER_URL
        defaultBookShouldBeFound("coverUrl.equals=" + DEFAULT_COVER_URL);

        // Get all the bookList where coverUrl equals to UPDATED_COVER_URL
        defaultBookShouldNotBeFound("coverUrl.equals=" + UPDATED_COVER_URL);
    }

    @Test
    @Transactional
    public void getAllBooksByCoverUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where coverUrl not equals to DEFAULT_COVER_URL
        defaultBookShouldNotBeFound("coverUrl.notEquals=" + DEFAULT_COVER_URL);

        // Get all the bookList where coverUrl not equals to UPDATED_COVER_URL
        defaultBookShouldBeFound("coverUrl.notEquals=" + UPDATED_COVER_URL);
    }

    @Test
    @Transactional
    public void getAllBooksByCoverUrlIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where coverUrl in DEFAULT_COVER_URL or UPDATED_COVER_URL
        defaultBookShouldBeFound("coverUrl.in=" + DEFAULT_COVER_URL + "," + UPDATED_COVER_URL);

        // Get all the bookList where coverUrl equals to UPDATED_COVER_URL
        defaultBookShouldNotBeFound("coverUrl.in=" + UPDATED_COVER_URL);
    }

    @Test
    @Transactional
    public void getAllBooksByCoverUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where coverUrl is not null
        defaultBookShouldBeFound("coverUrl.specified=true");

        // Get all the bookList where coverUrl is null
        defaultBookShouldNotBeFound("coverUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByCoverUrlContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where coverUrl contains DEFAULT_COVER_URL
        defaultBookShouldBeFound("coverUrl.contains=" + DEFAULT_COVER_URL);

        // Get all the bookList where coverUrl contains UPDATED_COVER_URL
        defaultBookShouldNotBeFound("coverUrl.contains=" + UPDATED_COVER_URL);
    }

    @Test
    @Transactional
    public void getAllBooksByCoverUrlNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where coverUrl does not contain DEFAULT_COVER_URL
        defaultBookShouldNotBeFound("coverUrl.doesNotContain=" + DEFAULT_COVER_URL);

        // Get all the bookList where coverUrl does not contain UPDATED_COVER_URL
        defaultBookShouldBeFound("coverUrl.doesNotContain=" + UPDATED_COVER_URL);
    }


    @Test
    @Transactional
    public void getAllBooksByPublisherIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        Publisher publisher = PublisherResourceIT.createEntity(em);
        em.persist(publisher);
        em.flush();
        book.setPublisher(publisher);
        bookRepository.saveAndFlush(book);
        Long publisherId = publisher.getId();

        // Get all the bookList where publisher equals to publisherId
        defaultBookShouldBeFound("publisherId.equals=" + publisherId);

        // Get all the bookList where publisher equals to publisherId + 1
        defaultBookShouldNotBeFound("publisherId.equals=" + (publisherId + 1));
    }


    @Test
    @Transactional
    public void getAllBooksByAuthorIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        Author author = AuthorResourceIT.createEntity(em);
        em.persist(author);
        em.flush();
        book.addAuthor(author);
        bookRepository.saveAndFlush(book);
        Long authorId = author.getId();

        // Get all the bookList where author equals to authorId
        defaultBookShouldBeFound("authorId.equals=" + authorId);

        // Get all the bookList where author equals to authorId + 1
        defaultBookShouldNotBeFound("authorId.equals=" + (authorId + 1));
    }


    @Test
    @Transactional
    public void getAllBooksByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        Category category = CategoryResourceIT.createEntity(em);
        em.persist(category);
        em.flush();
        book.setCategory(category);
        bookRepository.saveAndFlush(book);
        Long categoryId = category.getId();

        // Get all the bookList where category equals to categoryId
        defaultBookShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the bookList where category equals to categoryId + 1
        defaultBookShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookShouldBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].iSBN").value(hasItem(DEFAULT_I_SBN)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].numberOfPages").value(hasItem(DEFAULT_NUMBER_OF_PAGES)))
            .andExpect(jsonPath("$.[*].publishYear").value(hasItem(DEFAULT_PUBLISH_YEAR)))
            .andExpect(jsonPath("$.[*].coverUrl").value(hasItem(DEFAULT_COVER_URL)));

        // Check, that the count call also returns 1
        restBookMockMvc.perform(get("/api/books/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookShouldNotBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookMockMvc.perform(get("/api/books/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findById(book.getId()).get();
        // Disconnect from session so that the updates on updatedBook are not directly saved in db
        em.detach(updatedBook);
        updatedBook
            .iSBN(UPDATED_I_SBN)
            .title(UPDATED_TITLE)
            .price(UPDATED_PRICE)
            .numberOfPages(UPDATED_NUMBER_OF_PAGES)
            .publishYear(UPDATED_PUBLISH_YEAR)
            .coverUrl(UPDATED_COVER_URL);

        restBookMockMvc.perform(put("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBook)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getiSBN()).isEqualTo(UPDATED_I_SBN);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBook.getNumberOfPages()).isEqualTo(UPDATED_NUMBER_OF_PAGES);
        assertThat(testBook.getPublishYear()).isEqualTo(UPDATED_PUBLISH_YEAR);
        assertThat(testBook.getCoverUrl()).isEqualTo(UPDATED_COVER_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Create the Book

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc.perform(put("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Delete the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
