package dev.atito.domain.request.status;

public class PendingRequestStatus extends RequestStatus {
    public PendingRequestStatus() {
        super("Pending");
    }

    @Override
    public PendingRequestStatus clone() {
        return new PendingRequestStatus();
    }
}
