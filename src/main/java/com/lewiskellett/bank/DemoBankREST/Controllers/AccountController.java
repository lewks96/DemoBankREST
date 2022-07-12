package com.lewiskellett.bank.DemoBankREST.Controllers;

import com.lewiskellett.bank.DemoBankREST.AccountRepository;
import com.lewiskellett.bank.DemoBankREST.Types.Account;
import com.lewiskellett.bank.DemoBankREST.Types.AccountNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {
    private final AccountRepository repo;

    AccountController(AccountRepository repository) {
        this.repo = repository;
    }

    //TODO: Add AccountApplication class?
    Account newAccount(@RequestBody Account newAccount) {
        return repo.save(newAccount);
    }

    @GetMapping("/accounts/{firstName}/{lastName}")
    Account one(@PathVariable String firstName, @PathVariable String lastName) {
        List<Account> all = this.repo.findAll();
        for (Account a : all) {
            if (a.getFirstName().equals(firstName) &&
                    a.getLastName().equals(lastName)) {
                return a;
            }
        }
        throw new AccountNotFoundException(firstName + " " + lastName);
    }

    @DeleteMapping("/accounts/{uuid}")
    void deleteAccount(@PathVariable String uuid) {
        List<Account> all = this.repo.findAll();
        for (Account a : all) {
            if (a.getAccountUUID().equals(uuid)) {
                this.repo.delete(a);
                return;
            }
        }
        throw new AccountNotFoundException(uuid);
    }

}
