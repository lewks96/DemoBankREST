package com.lewiskellett.bank.DemoBankREST.Types;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Transaction {
    private @Id
    @GeneratedValue Long id;

    private String transactionID;
    private String sourceAccountID;
    private String destinationAccountID;
    private long amount;
    private final LocalDateTime timestamp;

    public Transaction() {
        this.timestamp = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        sb.append(UUID.randomUUID()).append(UUID.randomUUID());
        this.transactionID = sb.toString().replace("-", "");

    }

    public Transaction(String sourceAccountID, String destinationAccountID, long amount, LocalDateTime timestamp) {
        this.sourceAccountID = sourceAccountID;
        this.destinationAccountID = destinationAccountID;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Transaction(String ID) {
        this.sourceAccountID = "";
        this.destinationAccountID = "";
        this.amount = 0;
        this.timestamp = LocalDateTime.now();
        this.transactionID = ID;
    }

    public String getSourceAccountID() {
        return this.sourceAccountID;
    }

    public String getDestinationAccountID() {
        return this.destinationAccountID;
    }

    public long getAmount() {
        return this.amount;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public String getTransactionID() {
        return this.transactionID;
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
        return "Transaction {" + "id=" + this.transactionID + ", sourceAccountID='" + this.getSourceAccountID() +
                "'" + ", destinationAccountID='" + this.getDestinationAccountID() + "', amount='" + this.getAmount() + "'}";
    }


}
