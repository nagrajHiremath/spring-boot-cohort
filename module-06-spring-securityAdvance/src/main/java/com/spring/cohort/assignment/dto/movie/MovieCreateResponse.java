package com.spring.cohort.assignment.dto.movie;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MovieCreateResponse {
    Long id;

    String name;

    String description;

    String uploadUrl;
}
