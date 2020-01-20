package dev.atito.domain.request;

import dev.atito.domain.Resource;
import dev.atito.domain.request.body.RequestBody;
import dev.atito.domain.request.status.RequestStatus;

import java.util.UUID;

public class Request implements Resource {
    private UUID id;
    private RequestBody requestBody;
    private RequestStatus requestStatus;

    public Request(UUID id, RequestBody requestBody, RequestStatus requestStatus) {
        this.id = id;
        this.requestBody = requestBody;
        this.requestStatus = requestStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Request clone() {
        RequestBody newRequestBody = requestBody.clone();
        RequestStatus newRequestStatus = requestStatus.clone();
        return new Request(id, newRequestBody, newRequestStatus);
    }
}
