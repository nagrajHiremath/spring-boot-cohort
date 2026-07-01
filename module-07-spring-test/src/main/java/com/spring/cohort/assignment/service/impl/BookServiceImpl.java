package com.spring.cohort.assignment.service.impl;

import com.spring.cohort.assignment.dto.BookRequest;
import com.spring.cohort.assignment.dto.BookResponse;
import com.spring.cohort.assignment.entity.Book;
import com.spring.cohort.assignment.repository.BookRepository;
import com.spring.cohort.assignment.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    ModelMapper modelMapper;

    @Override
    public BookResponse createBook(BookRequest bookRequest) {

        Book book = modelMapper.map(bookRequest, Book.class);
        bookRepository.save(book);

        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public List<BookResponse> getBookList() {

        List<Book> bookList = bookRepository.findAll();
        return bookList.stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
    }

    @Override
    public BookResponse getBookById(UUID id) {

        Book book = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book not found"));
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public BookResponse updateBook(UUID id, BookRequest bookRequest) {

        Book book = bookRepository.findById(id).orElseThrow();
        modelMapper.map(bookRequest, book);

        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public List<BookResponse> getBookByTitle(String title) {
        List<Book> bookList = bookRepository.findByTitle(title);

        return bookList.stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
    }

    @Override
    public List<BookResponse> getBookAfterPublished(Date date) {
        List<Book> bookList = bookRepository.findByPublishDateAfter(date);

        return bookList.stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
    }

    @Override
    public List<BookResponse> getBookByAuthor(String authorName) {
        List<Book> bookList = bookRepository.findByAuthor_Name(authorName);

        return bookList.stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();
    }
}
