package com.spring.cohort.assignment.controller;

import com.spring.cohort.assignment.config.TestContainerConfiguration;
import com.spring.cohort.assignment.dto.BookRequest;
import com.spring.cohort.assignment.entity.Author;
import com.spring.cohort.assignment.entity.Book;
import com.spring.cohort.assignment.enums.BookCategory;
import com.spring.cohort.assignment.enums.Language;
import com.spring.cohort.assignment.repository.AuthorRepository;
import com.spring.cohort.assignment.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
public class BookControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    Author author = Author.builder()
            .name("John Doe")
            .email("john.doe@example.com")
            .biography("A brief biography")
            .dateOfBirth(new Date())
            .build();

    Book book = Book.builder()
            .title("The Great Novel")
            .language(Language.ENGLISH)
            .author(author)
            .pageCount(300)
            .availableCopies(5)
            .category(BookCategory.FICTION)
            .publishDate(new Date())
            .build();

    BookRequest bookRequest = BookRequest.builder()
            .title("The Great Novel")
            .language(Language.ENGLISH)
            .author(author)
            .pageCount(300)
            .category(BookCategory.FICTION)
            .build();

    @BeforeEach
    void cleanUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void testCreateBook_success(){
        authorRepository.save(author);
        webTestClient.post()
                .uri("/api/book") // Assumes your BookController mapping path is /api/book
                .bodyValue(bookRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.title").isEqualTo("The Great Novel");
    }

    @Test
    void testGetBookList_whenBooksExist_thenReturnList(){
        authorRepository.save(author);
        bookRepository.save(book);

        webTestClient.get()
                .uri("/api/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("The Great Novel");
    }

    @Test
    void testGetBookList_whenNoBooksExist_thenReturnEmptyList(){

        webTestClient.get()
                .uri("/api/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isEmpty();
    }

    @Test
    void testGetBookById_whenBookExists_thenReturnBookResponse(){
        authorRepository.save(author);
        Book book1 = bookRepository.save(book);

        webTestClient.get()
                .uri("/api/book/{id}", book1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("The Great Novel");
    }

    @Test
    void testGetBookById_whenBookDoesNotExist_thenReturnException(){
        webTestClient.get()
                .uri("/api/book/{id}", UUID.randomUUID())
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testUpdateBook_whenBookExists_thenUpdateAndReturnBookResponse(){
        authorRepository.save(author);
        Book book1 = bookRepository.save(book);

        BookRequest updatedBookRequest = BookRequest.builder()
                .title("Updated Title")
                .language(Language.ENGLISH)
                .author(author)
                .pageCount(300)
                .category(BookCategory.FICTION)
                .build();

        webTestClient.patch()
                .uri("/api/book/{id}", book1.getId())
                .bodyValue(updatedBookRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Updated Title");
    }

    @Test
    void testUpdateBook_whenBookDoesNotExist_thenReturnException(){
        webTestClient.patch()
                .uri("/api/book/{id}", UUID.randomUUID())
                .bodyValue(bookRequest)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testGetBookByTitle_whenMatchesExist_thenReturnList(){
        authorRepository.save(author);
        bookRepository.save(book);

        webTestClient.get()
                .uri("/api/book/{title}", "The Great Novel")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("The Great Novel");
    }

    @Test
    void testGetBookAfterPublished_whenMatchesExist_thenReturnList(){
        authorRepository.save(author);
        bookRepository.save(book);

        // Pass an older date to ensure our mock book falls after it
        Instant pastDate = Instant.now().minusSeconds(86400);

        webTestClient.get()
                .uri("/api/book/{date}", pastDate.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("The Great Novel");
    }

    @Test
    void testGetBookByAuthor_whenMatchesExist_thenReturnList(){
        authorRepository.save(author);
        bookRepository.save(book);

        webTestClient.get()
                .uri("/api/book/{authorName}", "John Doe")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].authorName").isEqualTo("John Doe");
    }
}
