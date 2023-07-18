package org.borave.model;

public class ApiResponse<T> {
    private boolean status;
    private String message;

    private T data;

    public ApiResponse(boolean status, String response, T data) {
        this.status = status;
        this.message = response;
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
