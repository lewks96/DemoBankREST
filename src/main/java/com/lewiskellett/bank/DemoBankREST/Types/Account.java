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
    private String accountID;
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

        this.accountID = generateAccountID();
    }

    public Account(String firstName, String lastName, double startingBalance, String accountID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = startingBalance;

        this.hasOverdraft = false;
        this.overdraftLimit = 0;

        this.accountID = accountID;
    }


    private static String generateAccountID() {
        return UUID.randomUUID().toString().replace("-", "");
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

    public String getAccountID() {
        return this.accountID;
    }

    public double getBalance() {
        return this.balance;
    }

    public void addOverdraft(double limit) {
        this.hasOverdraft = true;
        this.overdraftLimit = limit;
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

    public static Account CreateFrom(AccountApplication application) throws AccountApplicationException {
        if (application.hasOverdraft() && application.getOverdraftLimit() >= 0) {
            throw new AccountApplicationException(
                    ApplicationFailureReason.INVALID_OVERDRAFT_AMMOUNT,
                    application);
        }

        Account acc = new Account(application.getFirstName(), application.getLastName(), application.getFirstDeposit());
        acc.addOverdraft(application.getOverdraftLimit());
        return acc;
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
                && Objects.equals(this.accountID, account.accountID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.getFullName(), this.accountID);
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + this.id + ", name='" + this.getFullName() +
                "'" + ", accountID='" + this.accountID + "', balance='" + this.getBalance() + "'}";
    }

}
