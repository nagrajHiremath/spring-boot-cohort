package com.spring.cohort.assignment.controller;

import com.spring.cohort.assignment.dto.movie.MovieCreateRequest;
import com.spring.cohort.assignment.dto.movie.MovieCreateResponse;
import com.spring.cohort.assignment.dto.movie.MovieResponse;
import com.spring.cohort.assignment.entity.Movie;
import com.spring.cohort.assignment.service.MovieService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/movie")
@EnableMethodSecurity
public class MovieController {

    MovieService movieService;

    @PostMapping("/create")
    public ResponseEntity<MovieCreateResponse> uploadMovie(MovieCreateRequest movieCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovie(movieCreateRequest));
    }

    @GetMapping
    @PreAuthorize("@authService.hasPlan('FREE')")
    public ResponseEntity<List<Movie>> getMovieList(){
        return ResponseEntity.ok(movieService.getMovieList());
    }

    @GetMapping("/watch/{id}")
    @PreAuthorize("@authService.hasPlan('BASIC')")
    public ResponseEntity<Movie> watchMovie(@PathVariable Long id){
        return ResponseEntity.ok(movieService.watchMovie(id));
    }

    @GetMapping("/download/{id}")
    @PreAuthorize("@authService.hasPlan('PREMIUM')")
    public ResponseEntity<Movie> downloadMovie(@PathVariable Long id){
        return ResponseEntity.ok(movieService.downloadMovie(id));
    }

}
