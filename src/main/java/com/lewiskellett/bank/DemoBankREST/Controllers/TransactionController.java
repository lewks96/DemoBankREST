package com.lewiskellett.bank.DemoBankREST.Controllers;

import com.lewiskellett.bank.DemoBankREST.Repositories.AccountRepository;
import com.lewiskellett.bank.DemoBankREST.Repositories.TransactionRepository;
import com.lewiskellett.bank.DemoBankREST.Types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

/*
    TODO:
        * Implement transactions!
    FIXED:
        * Each of the mappings currently call repo.findAll(), there is probably
          a more efficient way to iterate the repo, look into it.
 */

@RestController
public class TransactionController {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);


    TransactionController(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/transactions/new")
    ResponseEntity<?> postNewTransaction(@RequestBody Transaction transaction) {
        if (transaction.getAmount() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Account> sourceOptional = accountRepository.findByIdString(transaction.getSourceAccountID());
        Optional<Account> destinationOptional = accountRepository.findByIdString(transaction.getDestinationAccountID());
        if (sourceOptional.isPresent() && destinationOptional.isPresent()) {
            Account source = sourceOptional.get();
            Account destination = destinationOptional.get();

            source.postTransaction(transaction);
            destination.postTransaction(transaction);
            transactionRepository.save(transaction);
            accountRepository.save(source);
            accountRepository.save(destination);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/transactions/all/{accountID}")
    ResponseEntity<?> all(@PathVariable String accountID) {
        Optional<Account> result = accountRepository.findByIdString(accountID);
        if (result.isPresent()) {
            return new ResponseEntity<>(transactionRepository.getAllTransactionsBySource(result.get()), HttpStatus.OK);
        }
        throw new AccountNotFoundException(accountID);
    }

    @GetMapping("/transactions/{transactionID}")
    ResponseEntity<?> fromID(@PathVariable String transactionID) {
        Optional<Transaction> result = transactionRepository.getTransactionById(transactionID);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
        throw new TransactionNotFoundException(transactionID);
    }


    @GetMapping("/transactions/month/{accountID}")
    ResponseEntity<?> recent(@PathVariable String accountID) {
        Optional<Account> result = accountRepository.findByIdString(accountID);
        if (result.isPresent()) {
            return new ResponseEntity<>(transactionRepository.getTransactionsRange(
                    result.get(),
                    LocalDateTime.now(),
                    LocalDateTime.now().minusMonths(1)),
                    HttpStatus.OK);
        }
        throw new AccountNotFoundException(accountID);
    }

    @GetMapping("/transactions/days/{accountID}/{days}")
    ResponseEntity<?> fromDaysAgo(@PathVariable String accountID, @PathVariable long days) {
        Optional<Account> result = accountRepository.findByIdString(accountID);
        if (result.isPresent()) {
            return new ResponseEntity<>(transactionRepository.getTransactionsRange(
                    result.get(),
                    LocalDateTime.now(),
                    LocalDateTime.now().minusDays(days)),
                    HttpStatus.OK);
        }
        throw new AccountNotFoundException(accountID);
    }

}
