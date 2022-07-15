package com.lewiskellett.bank.DemoBankREST.Controllers;

import com.lewiskellett.bank.DemoBankREST.Repositories.AccountRepository;
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
    private final AccountRepository accountRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    //@GetMapping("/accounts")
    List<Account> all() {
        return accountRepository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/accounts/new")
    ResponseEntity<?> newAccount(@RequestBody AccountApplication newAccount) {
        Account acc = Account.CreateFrom(newAccount);
        Optional<Account> result = accountRepository.findByAccount(acc);
        if (result.isPresent()) {
            // This shouldn't really ever throw?
            throw new AccountExistsException(acc.getAccountID());
        }
        return new ResponseEntity<>(accountRepository.save(acc), HttpStatus.OK);
    }

    @GetMapping("/accounts/{accountID}")
    ResponseEntity<?> one(@PathVariable String accountID) {
        Optional<Account> result = accountRepository.findByIdString(accountID);
        if (result.isPresent()) {
            return new ResponseEntity<>(accountRepository.save(result.get()), HttpStatus.OK);
        }
        throw new AccountNotFoundException(accountID);
    }

    @DeleteMapping("/accounts/{accountID}")
    ResponseEntity<?> deleteAccount(@PathVariable String accountID) {
        Optional<Account> result = accountRepository.findByIdString(accountID);
        if (result.isPresent()) {
            accountRepository.delete(result.get());
            return new ResponseEntity<>("{ \"result\": \"ok\" }", HttpStatus.OK);
        }
        throw new AccountNotFoundException(accountID);
    }

}
