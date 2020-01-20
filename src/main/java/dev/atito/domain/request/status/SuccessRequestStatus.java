package dev.atito.domain.request.status;

import dev.atito.domain.Resource;

public class SuccessRequestStatus extends RequestStatus {
    public Resource resource;

    public SuccessRequestStatus(Resource resource) {
        super("Success");
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public SuccessRequestStatus clone() {
        Resource newResource = resource.clone();
        return new SuccessRequestStatus(newResource);
    }
}
