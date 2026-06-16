package com.spring.cohort.assignment.repository;

import com.spring.cohort.assignment.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
