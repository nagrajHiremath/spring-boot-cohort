package com.spring.cohort.assignment.repository;

import com.spring.cohort.assignment.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Author findByEmail(String email);
}
