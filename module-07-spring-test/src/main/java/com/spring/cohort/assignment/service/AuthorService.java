package com.spring.cohort.assignment.service;

import com.spring.cohort.assignment.dto.AuthorRequest;
import com.spring.cohort.assignment.dto.AuthorResponse;

import java.util.List;
import java.util.UUID;

public interface AuthorService {
    AuthorResponse createAuthor(AuthorRequest authorRequest);

    List<AuthorResponse> getAuthorList();

    AuthorResponse getAuthorById(UUID id);

    AuthorResponse updateAuthor(UUID id, AuthorRequest authorRequest);

    void deleteAuthor(UUID id);
}
