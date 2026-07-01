package com.spring.cohort.assignment.controller;

import com.spring.cohort.assignment.dto.BookRequest;
import com.spring.cohort.assignment.dto.BookResponse;
import com.spring.cohort.assignment.entity.Book;
import com.spring.cohort.assignment.service.BookService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/book")
public class BookController {
    BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest bookRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookRequest));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getBookList(){
        return ResponseEntity.ok(bookService.getBookList());
    }

    @GetMapping("{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable UUID id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable UUID id, @RequestBody BookRequest bookRequest){
        return ResponseEntity.ok(bookService.updateBook(id, bookRequest));
    }

    @GetMapping("{title}")
    public ResponseEntity<List<BookResponse>> getBookByTitle(String title){
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    @GetMapping("{date}")
    public ResponseEntity<List<BookResponse>> getBookAfterPublished(Date date){
        return ResponseEntity.ok(bookService.getBookAfterPublished(date));
    }

    @GetMapping("{authorName}")
    public ResponseEntity<List<BookResponse>> getBookByAuthor(String authorName){
        return ResponseEntity.ok(bookService.getBookByAuthor(authorName));
    }

}
