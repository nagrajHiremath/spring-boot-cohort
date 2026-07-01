package com.spring.cohort.assignment.repository;

import com.spring.cohort.assignment.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByTitle(String title);

    List<Book> findByPublishDateAfter(Date date);

    List<Book> findByAuthor_Name(String authorName);
}
