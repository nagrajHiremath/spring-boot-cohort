package com.spring.cohort.assignment.dto;

import com.spring.cohort.assignment.entity.Author;
import com.spring.cohort.assignment.enums.BookCategory;
import com.spring.cohort.assignment.enums.Language;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.UUID;

@Data
public class BookResponse {

    UUID id;

    String title;

    Language language;

    Author author;

    Integer pageCount;

    Integer availableCopies;

    BookCategory category;
}
