package dev.atito.domain.request.body;

import dev.atito.domain.request.RequestType;

public class CreateAccountRequestBody extends RequestBody {
    public CreateAccountRequestBody() {
        super(RequestType.CREATE_ACCOUNT);
    }

    @Override
    public CreateAccountRequestBody clone() {
        return new CreateAccountRequestBody();
    }
}
