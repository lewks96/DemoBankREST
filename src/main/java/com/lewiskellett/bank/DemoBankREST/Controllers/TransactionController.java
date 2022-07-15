package com.lewiskellett.bank.DemoBankREST.Controllers;

import com.lewiskellett.bank.DemoBankREST.Repositories.AccountRepository;
import com.lewiskellett.bank.DemoBankREST.Repositories.TransactionRepository;
import com.lewiskellett.bank.DemoBankREST.Types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

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

    // Aggregate root
    // tag::get-aggregate-root[]
    //@GetMapping("/accounts")
    List<Account> all() {
        return accountRepository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/transactions/post")
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

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/transactions/all/{accountID}")
    ResponseEntity<?> one(@PathVariable String accountID) {
        Optional<Account> result = accountRepository.findByIdString(accountID);
        if (result.isPresent()) {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnorePaths("destinationAccountID")
                    .withIgnorePaths("amount")
                    .withIgnorePaths("timestamp")
                    .withMatcher("sourceAccountID", exact());

            Transaction dummy = new Transaction(accountID, "", 0, LocalDateTime.now());
            Example<Transaction> example = Example.of(dummy, matcher);

            List<Transaction> list = transactionRepository.findAll(example);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        throw new AccountNotFoundException(accountID);
    }

}
