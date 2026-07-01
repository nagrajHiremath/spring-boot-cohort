package com.spring.cohort.assignment.dto;

import com.spring.cohort.assignment.entity.Author;
import com.spring.cohort.assignment.enums.BookCategory;
import com.spring.cohort.assignment.enums.Language;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    String title;

    Language language;

    Author author;

    Integer pageCount;

    BookCategory category;
}
