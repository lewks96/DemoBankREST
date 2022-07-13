package com.lewiskellett.bank.DemoBankREST.Controllers;

import com.lewiskellett.bank.DemoBankREST.AccountRepository;
import com.lewiskellett.bank.DemoBankREST.Types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    TODO:
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
        //try {
        Account acc = Account.CreateFrom(newAccount);

        List<Account> accounts = all();
        for (Account a : accounts) {
            if (a.equals(acc)) {
                // This shouldn't really ever throw?
                throw new AccountExistsException(acc.getAccountID());
            }
        }

        return repo.save(acc);
        /*} catch (AccountApplicationException ex) {
            logger.error(ex.getMessage());
        } catch (AccountExistsException ex) {
            logger.error(ex.getMessage());
        }*/
        // return null;
    }

    @GetMapping("/account/{accountID}")
    Account one(@PathVariable String accountID) {
        List<Account> all = this.repo.findAll();
        for (Account a : all) {
            if (a.getAccountID().equals(accountID))
                return a;
        }
        throw new AccountNotFoundException(accountID);
    }

//    @GetMapping("/accounts/{firstName}/{lastName}")
//    Account one(@PathVariable String firstName, @PathVariable String lastName) {
//        List<Account> all = this.repo.findAll();
//        for (Account a : all) {
//            if (a.getFirstName().equals(firstName) &&
//                    a.getLastName().equals(lastName)) {
//                return a;
//            }
//        }
//        throw new AccountNotFoundException(firstName + " " + lastName);
//    }

    @DeleteMapping("/accounts/{accountID}")
    ResponseEntity<?> deleteAccount(@PathVariable String accountID) {
        List<Account> all = this.repo.findAll();
        for (Account a : all) {
            if (a.getAccountID().equals(accountID)) {
                this.repo.delete(a);
                return ResponseEntity.noContent().build();

            }
        }
        throw new AccountNotFoundException(accountID);
    }

}
