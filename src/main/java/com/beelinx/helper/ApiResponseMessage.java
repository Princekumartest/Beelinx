package com.beelinx.helper;

import lombok.Data;

@Data
public class ApiResponseMessage {

        private String message;
        private String status;
        private int statusCode;

        public ApiResponseMessage(String message, String status, int statusCode) {
            this.message = message;
            this.status = status;
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }


        @Override
        public String toString() {
            return "ApiResponseMessage{" +
                    "message='" + message + '\'' +
                    ", status='" + status + '\'' +
                    ", statusCode=" + statusCode +
                    '}';
        }

}
