package com.spring.cohort.assignment.repository;

import com.spring.cohort.assignment.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByName(String name);

    List<Author> findByBooksTitle(String title);
}
