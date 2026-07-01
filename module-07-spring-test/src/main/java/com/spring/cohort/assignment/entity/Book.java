package com.spring.cohort.assignment.entity;

import com.spring.cohort.assignment.enums.BookCategory;
import com.spring.cohort.assignment.enums.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String title;

    @Enumerated(EnumType.STRING)
    Language language;

    @ManyToOne
    Author author;

    Integer pageCount;

    Integer availableCopies;

    @Enumerated(EnumType.STRING)
    BookCategory category;

    Date publishDate = new Date();
}