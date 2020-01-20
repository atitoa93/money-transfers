package dev.atito.domain.request.body;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import dev.atito.domain.request.RequestType;

import java.math.BigDecimal;
import java.util.UUID;

public class RequestBodyFactory {
    private RequestBodyFactory() {
    }

    public static RequestBody getRequestBody(String jsonBody) throws Exception {
        try {
            ReadContext jsonContext = JsonPath.parse(jsonBody);

            RequestType requestType = RequestType.valueOf(jsonContext.read("$.requestType"));
            switch (requestType) {
                case CREATE_ACCOUNT: {
                    return new CreateAccountRequestBody();
                } case DEPOSIT_ACCOUNT: {
                    UUID accountId = UUID.fromString(jsonContext.read("$.accountId"));
                    BigDecimal amount = new BigDecimal((String) jsonContext.read("$.amount"));
                    return new DepositAccountRequestBody(accountId, amount);
                } case WITHDRAW_ACCOUNT: {
                    UUID accountId = UUID.fromString(jsonContext.read("$.accountId"));
                    BigDecimal amount = new BigDecimal((String) jsonContext.read("$.amount"));
                    return new WithdrawAccountRequestBody(accountId, amount);
                } case DELETE_ACCOUNT: {
                    UUID accountId = UUID.fromString(jsonContext.read("$.accountId"));
                    return new DeleteAccountRequestBody(accountId);
                } case TRANSFER: {
                    UUID fromAccountId = UUID.fromString(jsonContext.read("$.fromAccountId"));
                    UUID toAccountId = UUID.fromString(jsonContext.read("$.toAccountId"));
                    BigDecimal amount = new BigDecimal((String) jsonContext.read("$.amount"));
                    return new TransferRequestBody(fromAccountId, toAccountId, amount);
                } default: {
                    throw new Exception("Not supported request type");
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
