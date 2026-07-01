package com.spring.cohort.assignment.controller;

import com.spring.cohort.assignment.config.TestContainerConfiguration;
import com.spring.cohort.assignment.dto.AuthorRequest;
import com.spring.cohort.assignment.entity.Author;
import com.spring.cohort.assignment.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Date;
import java.util.UUID;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
public class AuthorControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    AuthorRepository authorRepository;

    Author author = Author.builder()
            .name("John Doe")
            .email("john.doe@example.com")
            .biography("A brief biography")
            .dateOfBirth(new Date())
            .build();

    AuthorRequest authorRequest = AuthorRequest.builder()
            .name("John Doe")
            .email("john.doe@example.com")
            .biography("A brief biography")
            .dateOfBirth(new Date())
            .build();

    @BeforeEach
    void setUp(){
        authorRepository.deleteAll();
    }

    @Test
    void testCreateAuthor_whenAuthorNotExistByEmail_thenCreate(){
        webTestClient.post()
                .uri("/api/author")
                .bodyValue(authorRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo("john.doe@example.com");
    }

    @Test
    void testCreateAuthor_whenAuthorExistByEmail_thenThrowException(){
        authorRepository.save(author);

        webTestClient.post()
                .uri("api/author")
                .bodyValue(authorRequest)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testGetAuthorList_whenAuthorExist_thenReturnList(){
        authorRepository.save(author);

        webTestClient.get()
                .uri("api/author")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].email").isEqualTo("john.doe@example.com");
    }

    @Test
    void testGetAuthorList_whenAuthorNotExist_thenReturnEmptyList(){
        webTestClient.get()
                .uri("api/author")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isEmpty();
    }

    @Test
    void testGetAuthorById_whenAuthorExist_thenReturnAuthorResponse(){
        authorRepository.save(author);

        webTestClient.get()
                .uri("/api/author/{id}", author.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo("john.doe@example.com");
    }

    @Test
    void testGetAuthorById_whenAuthorNotExist_thenReturnException(){
        webTestClient.get()
                .uri("/api/author/{id}", UUID.randomUUID())
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testUpdateAuthor_whenAuthorExist_thenUpdateAndReturnAuthorResponse(){
        authorRepository.save(author);

        AuthorRequest updatedAuthorRequest = AuthorRequest.builder()
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .biography("An updated biography")
                .dateOfBirth(new Date())
                .build();

        webTestClient.patch()
                .uri("/api/author/{id}", author.getId())
                .bodyValue(updatedAuthorRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo("jane.doe@example.com");
    }

    @Test
    void testUpdateAuthor_whenAuthorNotExist_thenReturnException(){
        webTestClient.patch()
                .uri("/api/author/{id}", UUID.randomUUID())
                .bodyValue(authorRequest)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testDeleteAuthor_whenAuthorExist_thenDeleteAndReturnNoContent(){
        authorRepository.save(author);

        webTestClient.delete()
                .uri("/api/author/{id}", author.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteAuthor_whenAuthorNotExist_thenReturnException(){
        webTestClient.delete()
                .uri("/api/author/{id}", UUID.randomUUID())
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
