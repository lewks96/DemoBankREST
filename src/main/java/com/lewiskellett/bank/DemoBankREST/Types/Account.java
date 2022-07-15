package com.lewiskellett.bank.DemoBankREST.Types;

import com.lewiskellett.bank.DemoBankREST.Util.BalanceFormat;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
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

    public Account(String accountID) {
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

    private void updateBalance(double transactionAmount)
            throws InsufficientBalanceException {
        if (transactionAmount < 0) {
            double newBalance = BalanceFormat.round(this.balance + transactionAmount);

            if (newBalance < 0) {
                if (!this.hasOverdraft || transactionAmount < this.overdraftLimit) {
                    throw new InsufficientBalanceException(this, transactionAmount);
                }
            }
            this.balance = newBalance;
        } else {
            this.balance += transactionAmount;
        }
    }

    public void postTransaction(Transaction transaction) throws InsufficientBalanceException {
        if (transaction.getSourceAccountID().equals(this.accountID)) {
            updateBalance(transaction.getAmount() * -1.0);
        } else {
            updateBalance(transaction.getAmount());
        }
    }

    public static Account CreateFrom(AccountApplication application) throws AccountApplicationException {
        if (application.hasOverdraft() && application.getOverdraftLimit() >= 0) {
            throw new AccountApplicationException(
                    ApplicationFailureReason.INVALID_OVERDRAFT_AMOUNT,
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
        return "Account{" + "id=" + this.getAccountID() + ", name='" + this.getFullName() +
                "', balance='" + this.getBalance() + "'}";
    }

}