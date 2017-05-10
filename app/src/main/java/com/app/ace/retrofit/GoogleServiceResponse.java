package com.app.ace.retrofit;

public class GoogleServiceResponse<T> {

    private T results;



    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
