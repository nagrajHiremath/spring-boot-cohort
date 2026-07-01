package com.spring.cohort.assignment.service;

import com.spring.cohort.assignment.dto.BookRequest;
import com.spring.cohort.assignment.dto.BookResponse;
import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface BookService {
    BookResponse createBook(BookRequest bookRequest);

    List<BookResponse> getBookList();

    BookResponse getBookById(UUID id);

    BookResponse updateBook(UUID id, BookRequest bookRequest);

    List<BookResponse> getBookByTitle(String title);

    List<BookResponse> getBookAfterPublished(Date date);

    List<BookResponse> getBookByAuthor(String authorName);
}
