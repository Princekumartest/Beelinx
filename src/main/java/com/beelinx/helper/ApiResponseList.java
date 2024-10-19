package com.beelinx.helper;

import lombok.Data;

@Data
public class ApiResponseList<T>{
        private String message;
        private String status;
        private int statusCode;
        private T data;

        public ApiResponseList(String message, String status, int statusCode, T data) {
            this.message = message;
            this.status = status;
            this.statusCode = statusCode;
            this.data = data;
        }
}
