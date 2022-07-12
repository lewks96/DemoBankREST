package com.lewiskellett.bank.DemoBankREST.Types;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
    private @Id
    @GeneratedValue Long id;
    private String accountUUID;
    private String firstName;
    private String lastName;
    private double balance;
    private boolean hasOverdraft;
    private double overdraftLimit;

    public Account() {
    }

    public Account(String firstName, String lastName, double startingBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = startingBalance;

        this.hasOverdraft = false;
        this.overdraftLimit = 0;

        this.accountUUID = generateAccountID();
    }

    private static String generateAccountID() {
        return UUID.randomUUID().toString();
    }


    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getAccountUUID() {
        return this.accountUUID;
    }

    public double getBalance() {
        return this.balance;
    }

    public void addOverdraft(double limit) {
        if (limit < 0) {
            this.hasOverdraft = true;
            this.overdraftLimit = limit;
        }
    }

    public void updateBalance(double transactionAmount)
            throws InsufficientBalanceException {
        double newBalance = this.balance - transactionAmount;

        if (newBalance < 0) {
            if (!this.hasOverdraft || transactionAmount < this.overdraftLimit) {
                throw new InsufficientBalanceException(this, transactionAmount);
            }
        }

        this.balance = newBalance;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Account))
            return false;
        Account account = (Account) o;
        return Objects.equals(this.id, account.id)
                && Objects.equals(this.getFullName(), account.getFullName())
                && Objects.equals(this.accountUUID, account.accountUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.getFullName(), this.accountUUID);
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + this.id + ", name='" + this.getFullName() +
                '\'' + ", uuid='" + this.accountUUID + '\'' + '}';
    }

}
