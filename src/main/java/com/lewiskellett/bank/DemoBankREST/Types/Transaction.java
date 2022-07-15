package com.lewiskellett.bank.DemoBankREST.Types;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transaction {
    private @Id
    @GeneratedValue Long id;

    private String sourceAccountID;
    private String destinationAccountID;
    private long amount;
    private LocalDateTime timestamp;

    public Transaction() {
    }

    public Transaction(String sourceAccountID, String destinationAccountID, long amount, LocalDateTime timestamp) {
        this.sourceAccountID = sourceAccountID;
        this.destinationAccountID = destinationAccountID;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getSourceAccountID() {
        return sourceAccountID;
    }

    public String getDestinationAccountID() {
        return destinationAccountID;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Transaction))
            return false;
        Transaction account = (Transaction) o;
        return Objects.equals(this.id, account.id)
                && Objects.equals(this.getSourceAccountID(), account.getSourceAccountID())
                && Objects.equals(this.getDestinationAccountID(), account.getDestinationAccountID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.getSourceAccountID(), this.getDestinationAccountID(), this.getAmount());
    }

    @Override
    public String toString() {
        return "Transaction {" + "id=" + this.id + ", sourceAccountID='" + this.getSourceAccountID() +
                "'" + ", destinationAccountID='" + this.getDestinationAccountID() + "', amount='" + this.getAmount() + "'}";
    }
}
