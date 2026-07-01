package com.spring.cohort.assignment.repository;

import com.spring.cohort.assignment.config.TestContainerConfiguration;
import com.spring.cohort.assignment.entity.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import java.util.UUID;

@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    Author author;

    @BeforeEach
    void setUp(){
        author = Author.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .biography("A brief biography.")
                .dateOfBirth(new Date(1980, 0, 1))
                .build();
    }

    @Test
    void testFindByEmail_whenAuthorExist_thenReturnAuthor(){
        authorRepository.save(author);
        Author foundAuthor = authorRepository.findByEmail("john.doe@example.com");

        assertThat(foundAuthor).isNotNull();
        assertThat(foundAuthor.getId()).isEqualTo(author.getId());
    }

    @Test
    void testFindByEmail_whenAuthorDoesNotExist_thenReturnNull(){
        Author foundAuthor = authorRepository.findByEmail("non.existent@example.com");
        assertThat(foundAuthor).isNull();
    }
}
