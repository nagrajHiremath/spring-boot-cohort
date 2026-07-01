package com.spring.cohort.assignment.controller;

import com.spring.cohort.assignment.dto.AuthorRequest;
import com.spring.cohort.assignment.dto.AuthorResponse;
import com.spring.cohort.assignment.service.AuthorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/author")
public class AuthorController {
    
    AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody AuthorRequest authorRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.createAuthor(authorRequest));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAuthorList(){
        return ResponseEntity.ok(authorService.getAuthorList());
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable UUID id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable UUID id, @RequestBody AuthorRequest authorRequest){
        return ResponseEntity.ok(authorService.updateAuthor(id, authorRequest));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id){
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

}
