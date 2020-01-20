package dev.atito.domain.request.status;

import dev.atito.domain.Cloneable;

public abstract class RequestStatus implements Cloneable<RequestStatus> {
    public final String status;

    protected RequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public abstract RequestStatus clone();
}
