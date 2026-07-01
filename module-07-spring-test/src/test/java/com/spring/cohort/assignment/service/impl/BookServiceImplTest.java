package com.spring.cohort.assignment.service.impl;

import com.spring.cohort.assignment.dto.AuthorResponse;
import com.spring.cohort.assignment.dto.BookRequest;
import com.spring.cohort.assignment.dto.BookResponse;
import com.spring.cohort.assignment.entity.Book;
import com.spring.cohort.assignment.enums.Language;
import com.spring.cohort.assignment.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.DATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    BookRepository bookRepository;

    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    BookServiceImpl bookService;

    BookRequest bookRequest;
    BookResponse bookResponse;
    Book book;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id(UUID.randomUUID())
                .title("Sample Book")
                .language(Language.ENGLISH)
                .pageCount(300)
                .availableCopies(10)
                .publishDate(new java.util.Date())
                .build();

        bookRequest = modelMapper.map(book, BookRequest.class);
        bookResponse = modelMapper.map(book, BookResponse.class);
    }

    @Test
    void testCreateBook(){
        bookService.createBook(bookRequest);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testGetBookList_whenBookNotExist_thenReturnEmptyList(){

        when(bookRepository.findAll()).thenReturn(List.of());

        List<BookResponse> list = bookService.getBookList();

        assertThat(list).isEmpty();
    }

    @Test
    void testGetBookList_whenBookExist_thenReturnBookList(){

        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookResponse> list = bookService.getBookList();

        assertThat(list).isNotEmpty();
        assertThat(list).contains(bookResponse);
    }

    @Test
    void testGetBookById_whenBookNotExist_thenThrowException(){

        when(bookRepository.findById(book.getId())).thenThrow(new RuntimeException("Book not found"));

        assertThatThrownBy(() -> bookService.getBookById(book.getId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Book not found");
    }

    @Test
    void testGetBookById_whenBookExist_thenReturnBookResponse(){

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        BookResponse result = bookService.getBookById(book.getId());

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(book.getId());
    }

    @Test
    void testUpdateBook_whenBookExist_thenUpdateAndReturnBookResponse(){

        when(bookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));

        BookResponse result = bookService.updateBook(book.getId(), bookRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(book.getId());
        assertThat(result.getTitle()).isEqualTo(bookRequest.getTitle());
    }

    @Test
    void testUpdateBook_whenBookNotExist_thenThrowException(){

        when(bookRepository.findById(book.getId())).thenThrow(new RuntimeException("Book not found"));

        assertThatThrownBy(() -> bookService.updateBook(book.getId(), bookRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Book not found");
    }

    @Test
    void testGetBookByTitle_whenBookNotExist_thenReturnEmptyList(){

        when(bookRepository.findByTitle(book.getTitle())).thenReturn(List.of());

        List<BookResponse> list = bookService.getBookByTitle(book.getTitle());

        assertThat(list).isEmpty();
    }

    @Test
    void testGetBookByTitle_whenBookExist_thenReturnBookList(){

        when(bookRepository.findByTitle(book.getTitle())).thenReturn(List.of(book));

        List<BookResponse> list = bookService.getBookByTitle(book.getTitle());

        assertThat(list).isNotEmpty();
        assertThat(list).contains(bookResponse);
    }

    @Test
    void testGetBookAfterPublished_whenBookNotExist_thenReturnEmptyList(){

        when(bookRepository.findByPublishDateAfter(book.getPublishDate())).thenReturn(List.of());

        List<BookResponse> list = bookService.getBookAfterPublished(book.getPublishDate());

        assertThat(list).isEmpty();
    }

    @Test
    void testGetBookAfterPublished_whenBookExist_thenReturnBookList(){

        when(bookRepository.findByPublishDateAfter(book.getPublishDate())).thenReturn(List.of(book));

        List<BookResponse> list = bookService.getBookAfterPublished(book.getPublishDate());

        assertThat(list).isNotEmpty();
        assertThat(list).contains(bookResponse);
    }

    @Test
    void testGetBookByAuthor_whenBookNotExist_thenReturnEmptyList(){

        when(bookRepository.findByAuthor_Name(ArgumentMatchers.anyString())).thenReturn(List.of());
        List<BookResponse> list = bookService.getBookByAuthor("John Doe");

        assertThat(list).isEmpty();
    }

    @Test
    void testGetBookByAuthor_whenBookExist_thenReturnBookList(){

        when(bookRepository.findByAuthor_Name(ArgumentMatchers.anyString())).thenReturn(List.of(book));

        List<BookResponse> list = bookService.getBookByAuthor("John Doe");

        assertThat(list).isNotEmpty();
        assertThat(list).contains(bookResponse);
    }
}
