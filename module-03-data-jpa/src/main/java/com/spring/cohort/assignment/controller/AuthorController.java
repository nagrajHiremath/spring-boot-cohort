package com.spring.cohort.assignment.controller;

import com.spring.cohort.assignment.entity.Author;
import com.spring.cohort.assignment.entity.Book;
import com.spring.cohort.assignment.repository.AuthorRepository;
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
@RequestMapping("/api/author")
public class AuthorController {
    AuthorRepository authorRepository;

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author){
        return ResponseEntity.status(HttpStatus.CREATED).body(authorRepository.save(author));
    }
    @GetMapping
    public ResponseEntity<List<Author>> getAuthorList(){
        return ResponseEntity.ok(authorRepository.findAll());
    }
    @GetMapping("{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        return ResponseEntity.ok(authorRepository.findById(id).orElseThrow());
    }
    @PatchMapping
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author){
        return ResponseEntity.ok(authorRepository.save(author));
    }
    @GetMapping("{name}")
    public ResponseEntity<List<Author>> getAuthorByName(String name){
        return ResponseEntity.ok(authorRepository.findByName(name));
    }
    @GetMapping("{title}")
    public ResponseEntity<List<Author>> getAuthorByBookTitle(String title){
        return ResponseEntity.ok(authorRepository.findByBooksTitle(title));
    }

}
