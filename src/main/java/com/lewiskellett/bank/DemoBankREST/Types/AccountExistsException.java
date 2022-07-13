package com.lewiskellett.bank.DemoBankREST.Types;

public class AccountExistsException extends RuntimeException {

    public AccountExistsException(String id) {
        super("Account '" + id + "' already exists");
    }

}
