package com.lewiskellett.bank.DemoBankREST.Types;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String id) {
        super("Can't find transaction'" + id + "'");
    }

}
