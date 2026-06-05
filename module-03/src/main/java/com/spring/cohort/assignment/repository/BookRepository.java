package com.spring.cohort.assignment.repository;

import com.spring.cohort.assignment.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByTitle(String title);

    List<Book> findByPublishedAtAfter(Instant date);

    List<Book> findByAuthorsName(String authorName);

}
