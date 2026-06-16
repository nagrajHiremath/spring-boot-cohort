package com.spring.cohort.assignment.service;

import com.spring.cohort.assignment.dto.movie.MovieCreateRequest;
import com.spring.cohort.assignment.dto.movie.MovieCreateResponse;
import com.spring.cohort.assignment.dto.movie.MovieResponse;
import com.spring.cohort.assignment.entity.Movie;
import com.spring.cohort.assignment.repository.MovieRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieService {

    MovieRepository movieRepository;

    public MovieCreateResponse createMovie(MovieCreateRequest movieCreateRequest) {

        Movie movie = Movie.builder()
                .name(movieCreateRequest.getName())
                .description(movieCreateRequest.getDescription())
                .watchUrl("")
                .uploadUrl("/upload/here")
                .build();

        movieRepository.save(movie);

        return MovieCreateResponse.builder()
                .id(movie.getId())
                .name(movieCreateRequest.getName())
                .description(movieCreateRequest.getDescription())
                .uploadUrl(movie.getUploadUrl())
                .build();
    }

    public List<Movie> getMovieList() {
        return movieRepository.findAll();
    }

    public Movie watchMovie(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    public Movie downloadMovie(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }
}
