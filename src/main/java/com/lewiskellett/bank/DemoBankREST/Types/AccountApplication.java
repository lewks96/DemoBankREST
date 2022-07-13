package com.lewiskellett.bank.DemoBankREST.Types;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class AccountApplication {
    private @Id
    @GeneratedValue Long id;
    private final String firstName;
    private final String lastName;
    private final double firstDeposit;
    private final boolean hasOverdraft;
    private final double overdraftLimit;

    public AccountApplication(String firstName, String lastName, double firstDeposit, boolean hasOverdraft, double overdraftLimit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstDeposit = firstDeposit;
        this.hasOverdraft = hasOverdraft;
        this.overdraftLimit = overdraftLimit;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getFirstDeposit() {
        return firstDeposit;
    }

    public boolean hasOverdraft() {
        return hasOverdraft;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof AccountApplication))
            return false;
        AccountApplication account = (AccountApplication) o;
        return Objects.equals(this.firstName, account.getFirstName())
                && Objects.equals(this.lastName, account.getLastName())
                && Objects.equals(this.firstDeposit, account.getFirstDeposit())
                && Objects.equals(this.hasOverdraft, account.hasOverdraft())
                && Objects.equals(this.overdraftLimit, account.getOverdraftLimit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.firstDeposit, this.hasOverdraft, this.overdraftLimit);
    }

    @Override
    public String toString() {
        return "Account{" + "firstName='" + this.firstName + "', lastName='" + this.lastName +
                "'" + ", firstDeposit='" + this.firstDeposit + "', hasOverdraft='" + this.hasOverdraft +
                "', overdraftLimit='" + this.overdraftLimit + "'}";
    }

}
