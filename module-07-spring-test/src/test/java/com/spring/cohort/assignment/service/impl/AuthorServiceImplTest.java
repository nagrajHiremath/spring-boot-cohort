package com.spring.cohort.assignment.service.impl;

import com.spring.cohort.assignment.dto.AuthorRequest;
import com.spring.cohort.assignment.dto.AuthorResponse;
import com.spring.cohort.assignment.entity.Author;
import com.spring.cohort.assignment.repository.AuthorRepository;
import com.spring.cohort.assignment.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @Mock
    AuthorRepository authorRepository;

    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    AuthorServiceImpl authorService;

    AuthorRequest authorRequest;
    AuthorResponse authorResponse;
    Author author;

    @BeforeEach
    void setUp(){
        author = Author.builder()
                .id(UUID.randomUUID())
                .email("nagraj@gmail.com")
                .name("nagraj")
                .biography("Nagraj is bestseller author of book You as Life")
                .dateOfBirth(new Date(30/ 3 /1999))
                .build();

        authorRequest = modelMapper.map(author, AuthorRequest.class);
        authorResponse = modelMapper.map(author, AuthorResponse.class);

    }

    @Test
    void testCreateAuthor_whenAuthorNotExistByEmail_thenCreateAuthor(){

        when(authorRepository.findByEmail(any())).thenReturn(null);

        AuthorResponse result = authorService.createAuthor(authorRequest);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(authorRequest.getEmail());

    }

    @Test
    void testCreateAuthor_whenAuthorExistByEmail_thenThrow(){

        when(authorRepository.findByEmail(any())).thenReturn(author);

        assertThatThrownBy(()-> authorService.createAuthor(authorRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Author already exist by this email");
    }

    @Test
    void testGetAuthorList_whenAuthorListExist_thenReturnEmptyList(){

        when(authorRepository.findAll()).thenReturn(List.of());

        List<AuthorResponse> list = authorService.getAuthorList();

        assertThat(list).isEmpty();
    }

    @Test
    void testGetAuthorById_whenAuthorNotExist_thenThrowException(){

        when(authorRepository.findById(author.getId())).thenThrow(new RuntimeException("Author not found"));

        assertThatThrownBy(() -> authorService.getAuthorById(author.getId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Author not found");
    }

    @Test
    void testGetAuthorById_whenAuthorExist_thenReturnAuthorResponse(){

        when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));

        AuthorResponse result = authorService.getAuthorById(author.getId());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(author.getId());
    }

    @Test
    void testUpdateAuthor_whenAuthorExist_thenUpdateAndReturnAuthorResponse(){

        when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));

        AuthorResponse result = authorService.updateAuthor(author.getId(), authorRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(author.getId());
        assertThat(result.getEmail()).isEqualTo(authorRequest.getEmail());
    }

    @Test
    void testUpdateAuthor_whenAuthorNotExist_thenThrowException(){

        when(authorRepository.findById(author.getId())).thenThrow(new RuntimeException("Author not found"));

        assertThatThrownBy(() -> authorService.updateAuthor(author.getId(), authorRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Author not found");
    }

    @Test
    void testDeleteAuthor_whenAuthorNotExist_thenThrowException(){

        when(authorRepository.findById(author.getId())).thenThrow(new RuntimeException("Author not found"));

        assertThatThrownBy(() -> authorService.deleteAuthor(author.getId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Author not found");
    }

    @Test
    void testDeleteAuthor_whenAuthorExist_thenDelete(){

        when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));

        authorService.deleteAuthor(author.getId());
        verify(authorRepository).delete(author);
    }


}
