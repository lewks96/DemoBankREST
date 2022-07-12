package com.lewiskellett.bank.DemoBankREST.Types;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String id) {
        super("Can't find account '" + id + "'");
    }

}
