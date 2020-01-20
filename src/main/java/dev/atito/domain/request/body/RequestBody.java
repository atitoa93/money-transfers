package dev.atito.domain.request.body;

import dev.atito.domain.Cloneable;
import dev.atito.domain.request.RequestType;

public abstract class RequestBody implements Cloneable<RequestBody> {
    private final RequestType requestType;

    protected RequestBody(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public abstract RequestBody clone();
}