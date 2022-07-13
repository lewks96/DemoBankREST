package com.lewiskellett.bank.DemoBankREST.Controllers;

import com.lewiskellett.bank.DemoBankREST.AccountRepository;
import com.lewiskellett.bank.DemoBankREST.Types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
    TODO:
        * Implement transactions!
    FIXED:
        * Each of the mappings currently call repo.findAll(), there is probably
          a more efficient way to iterate the repo, look into it.
 */

@RestController
public class AccountController {
    private final AccountRepository repo;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);


    AccountController(AccountRepository repository) {
        this.repo = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    //@GetMapping("/accounts")
    List<Account> all() {
        return repo.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/accounts/new")
    Account newAccount(@RequestBody AccountApplication newAccount) {
        Account acc = Account.CreateFrom(newAccount);
        Optional<Account> result = repo.findByAccount(acc);

        if (result.isPresent()) {
            // This shouldn't really ever throw?
            throw new AccountExistsException(acc.getAccountID());
        }

        return repo.save(acc);
    }

    @GetMapping("/account/{accountID}")
    Account one(@PathVariable String accountID) {
        Optional<Account> result = repo.findByIdString(accountID);
        if (result.isPresent()) {
            return result.get();
        }
        throw new AccountNotFoundException(accountID);
    }

    @DeleteMapping("/accounts/{accountID}")
    ResponseEntity<?> deleteAccount(@PathVariable String accountID) {
        Optional<Account> result = repo.findByIdString(accountID);
        if (result.isPresent()) {
            repo.delete(result.get());
            return new ResponseEntity<>("{ \"result\": \"ok\" }", HttpStatus.OK);
        }

        throw new AccountNotFoundException(accountID);
    }

}
