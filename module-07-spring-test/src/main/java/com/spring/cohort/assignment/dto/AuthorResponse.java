package com.spring.cohort.assignment.dto;

import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Data
public class AuthorResponse {
    UUID id;

    String name;

    String email;

    String biography;

    Date dateOfBirth;
}
