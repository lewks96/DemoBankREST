package com.lewiskellett.bank.DemoBankREST.Repositories;

import com.lewiskellett.bank.DemoBankREST.Types.Account;
import com.lewiskellett.bank.DemoBankREST.Types.Transaction;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    default List<Transaction> getAllTransactionsBySource(Account account) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("transactionID")
                .withIgnorePaths("destinationAccountID")
                .withIgnorePaths("amount")
                .withIgnorePaths("timestamp")
                .withMatcher("sourceAccountID", exact());

        Transaction dummy = new Transaction(account.getAccountID(), "", 0, LocalDateTime.now());
        Example<Transaction> example = Example.of(dummy, matcher);
        return this.findAll(example);
    }

    default List<Transaction> getAllTransactionsByDestination(Account account) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("transactionID")
                .withIgnorePaths("sourceAccountID")
                .withIgnorePaths("amount")
                .withIgnorePaths("timestamp")
                .withMatcher("destinationAccountID", exact());

        Transaction dummy = new Transaction(account.getAccountID(), "", 0, LocalDateTime.now());
        Example<Transaction> example = Example.of(dummy, matcher);
        return this.findAll(example);
    }

    default Optional<Transaction> getTransactionById(String id) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("sourceAccountID")
                .withIgnorePaths("destinationAccountID")
                .withIgnorePaths("amount")
                .withIgnorePaths("timestamp")
                .withMatcher("transactionID", exact());

        Transaction dummy = new Transaction(id);
        Example<Transaction> example = Example.of(dummy, matcher);
        return this.findOne(example);
    }

    default HashMap<LocalDateTime, Transaction> getTransactionsRange(Account account, LocalDateTime start, LocalDateTime end) {
        List<Transaction> set1 = this.getAllTransactionsBySource(account);
        List<Transaction> set2 = this.getAllTransactionsByDestination(account);

        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(set1);
        transactions.addAll(set2);

        HashMap<LocalDateTime, Transaction> sortedTransactions = new HashMap<>();
        for (Transaction t : transactions) {
            LocalDateTime timestamp = t.getTimestamp();
            if (timestamp.isBefore(start) && timestamp.isAfter(end)) {
                sortedTransactions.put(t.getTimestamp(), t);
            }
        }
        return sortedTransactions;
    }
}