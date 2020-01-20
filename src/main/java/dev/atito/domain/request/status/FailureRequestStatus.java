package dev.atito.domain.request.status;

public class FailureRequestStatus extends RequestStatus {
    public String reason;

    public FailureRequestStatus(String reason) {
        super("Failure");

        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public FailureRequestStatus clone() {
        return new FailureRequestStatus(reason);
    }
}
