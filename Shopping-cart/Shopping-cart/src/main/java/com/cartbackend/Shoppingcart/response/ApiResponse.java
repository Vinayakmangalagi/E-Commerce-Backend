package com.cartbackend.Shoppingcart.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class ApiResponse {
    private String message;
    private Object data;

    // Default no-arg constructor
    public ApiResponse() {}

    // Constructor with parameters
    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
