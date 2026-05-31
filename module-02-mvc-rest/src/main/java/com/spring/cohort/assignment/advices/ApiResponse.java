package com.spring.cohort.assignment.advices;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ApiResponse<T> {
    LocalDate timeStamp;
    T data;
    ApiError apiError;

    ApiResponse(){
        timeStamp = LocalDate.now();
    }
    ApiResponse(T data){
        this();
        this.data = data;
    }
    ApiResponse(ApiError apiError){
        this();
        this.apiError = apiError;
    }
}
