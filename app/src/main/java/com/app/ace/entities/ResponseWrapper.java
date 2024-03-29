package com.app.ace.entities;

public class ResponseWrapper<T> {

    private String Message="";
    private String Response="";
    private int UserDeleted;
    private T Result;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    public int getUserDeleted() {
        return UserDeleted;
    }

    public void setUserDeleted(int userDeleted) {
        UserDeleted = userDeleted;
    }
}
