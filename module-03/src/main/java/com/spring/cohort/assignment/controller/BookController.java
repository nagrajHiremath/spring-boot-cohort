package com.spring.cohort.assignment.controller;

import com.spring.cohort.assignment.entity.Book;
import com.spring.cohort.assignment.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/book")
public class BookController {
    BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.save(book));
    }
    @GetMapping
    public ResponseEntity<List<Book>> getBookList(){
        return ResponseEntity.ok(bookRepository.findAll());
    }
    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookRepository.findById(id).orElseThrow());
    }
    @PatchMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        return ResponseEntity.ok(bookRepository.save(book));
    }
    @GetMapping("{title}")
    public ResponseEntity<List<Book>> getBookByTitle(String title){
        return ResponseEntity.ok(bookRepository.findByTitle(title));
    }
    @GetMapping("{date}")
    public ResponseEntity<List<Book>> getBookAfterPublished(Instant date){
        return ResponseEntity.ok(bookRepository.findByPublishedAtAfter(date));
    }
    @GetMapping("{authorName}")
    public ResponseEntity<List<Book>> getBookByAuthor(String authorName){
        return ResponseEntity.ok(bookRepository.findByAuthorsName(authorName));
    }

}
