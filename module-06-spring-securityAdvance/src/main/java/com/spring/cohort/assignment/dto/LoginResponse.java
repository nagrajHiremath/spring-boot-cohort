package com.spring.cohort.assignment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    String accessToken;
    String refreshToken;
}
