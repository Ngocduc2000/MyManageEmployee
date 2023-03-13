package com.example.manageemployee.response;

public class ResponseResult {
    private String message;
    private Boolean isValid;


    public ResponseResult() {}
    public ResponseResult(String message) {
        this.message = message;
    }
    public ResponseResult(Boolean isValid) {
        this.isValid = isValid;
    }
    public ResponseResult(Boolean isValid, String message){
        this.isValid = isValid;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public boolean isValid() {
        return isValid;
    }
}
