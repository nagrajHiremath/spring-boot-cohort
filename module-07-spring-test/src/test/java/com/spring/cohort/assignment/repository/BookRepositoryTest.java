package com.spring.cohort.assignment.repository;

import com.spring.cohort.assignment.config.TestContainerConfiguration;
import com.spring.cohort.assignment.entity.Author;
import com.spring.cohort.assignment.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    Book book;
    Author author;

    @BeforeEach
    void setUp(){
        author = Author.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .biography("A brief biography.")
                .dateOfBirth(new Date(1980, 0, 1))
                .build();

        book = Book.builder()
                .title("John Doe")
                .publishDate(new Date(2020, 0, 1))
                .author(author)
                .build();
    }

    @Test
    void testFindByTitle_whenBookExist_thenReturnBookList(){
        authorRepository.save(author);
        bookRepository.save(book);
        List<Book> foundBook = bookRepository.findByTitle("John Doe");

        assertThat(foundBook).isNotNull();
        assertThat(foundBook.get(0).getId()).isEqualTo(book.getId());
    }

    @Test
    void testFindByTitle_whenBookDoesNotExist_thenReturnEmptyList(){
        List<Book> foundBook = bookRepository.findByTitle("Non Existent Book");
        assertThat(foundBook).isEmpty();
    }

    @Test
    void testFindByPublishDateAfter_whenBookExist_thenReturnBookList(){
        authorRepository.save(author);
        bookRepository.save(book);
        List<Book> foundBook = bookRepository.findByPublishDateAfter(new Date(2019, 0, 1));
        assertThat(foundBook).isNotNull();
        assertThat(foundBook.get(0).getId()).isEqualTo(book.getId());
    }

    @Test
    void testFindByPublishDateAfter_whenBookNotExist_thenReturnBookList(){
        List<Book> foundBook = bookRepository.findByPublishDateAfter(new Date(2021, 0, 1));
        assertThat(foundBook).isEmpty();
    }

    @Test
    void testFindByAuthorName_whenBookExist_thenReturnBookList(){
        authorRepository.save(author);
        bookRepository.save(book);
        List<Book> foundBook = bookRepository.findByAuthor_Name("John Doe");
        assertThat(foundBook).isNotNull();
        assertThat(foundBook.get(0).getId()).isEqualTo(book.getId());
    }

    @Test
    void testFindByAuthorName_whenBookNotExist_thenReturnBookList(){
        List<Book> foundBook = bookRepository.findByAuthor_Name("Non Existent Author");
        assertThat(foundBook).isEmpty();
    }

}
