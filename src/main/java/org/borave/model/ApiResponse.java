package org.borave.model;

public class ApiResponse<T> {
    private boolean status;
    private String response;

    private T data;

    public ApiResponse(boolean status, String response, T data) {
        this.status = status;
        this.response = response;
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
