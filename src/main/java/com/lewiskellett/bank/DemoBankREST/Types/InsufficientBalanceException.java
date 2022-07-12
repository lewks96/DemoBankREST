package com.lewiskellett.bank.DemoBankREST.Types;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super();
    }


    public InsufficientBalanceException(Account target, double amount) {
        super("Insufficient balance '" + target.getBalance() +
                "' on account '" + target.getAccountUUID() +
                "' to complete transaction of '" + amount + "'.");
    }

}
