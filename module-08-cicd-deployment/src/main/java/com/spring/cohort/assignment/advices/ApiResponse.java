package com.spring.cohort.assignment.advices;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ApiResponse<T> {
    LocalDate timeStamp;
    T data;
    ApiError apiError;

    public ApiResponse(){
        timeStamp = LocalDate.now();
    }
    public ApiResponse(T data){
        this();
        this.data = data;
    }
    public ApiResponse(ApiError apiError){
        this();
        this.apiError = apiError;
    }
}
