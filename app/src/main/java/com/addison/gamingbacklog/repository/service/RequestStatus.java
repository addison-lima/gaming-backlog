package com.addison.gamingbacklog.repository.service;

public class RequestStatus {

    private final RequestState mRequestState;

    public RequestStatus(RequestState requestState) {
        mRequestState = requestState;
    }

    public RequestState getRequestState() {
        return mRequestState;
    }

    public enum RequestState {
        LOADING,
        SUCCESS,
        FAILURE
    }
}
