package com.lewiskellett.bank.DemoBankREST.Types;

public class AccountApplicationException extends RuntimeException {


    public AccountApplicationException(ApplicationFailureReason reason, AccountApplication application) {
        super("Application failed, Reason='" + reason.name() + "', application='" + application.toString() + "'");
    }

}
