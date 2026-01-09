package com.springbootCohort.Module2.advices;

import java.time.LocalDateTime;

public class ApiResponse <T>{
    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();     // calling the default constructor from here
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }
}
