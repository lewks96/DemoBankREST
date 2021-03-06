package com.lewiskellett.bank.DemoBankREST.Repositories;


import com.lewiskellett.bank.DemoBankREST.Types.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

public interface AccountRepository extends JpaRepository<Account, Long> {

    default boolean accountExists(Account account) {
        return findByAccount(account).isPresent();
    }

    default Optional<Account> findByIdString(String accountID) {
        Account query = new Account(accountID);
        return findByAccount(query);
    }

    default Optional<Account> findByAccount(Account account) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("firstName")
                .withIgnorePaths("lastName")
                .withIgnorePaths("balance")
                .withIgnorePaths("hasOverdraft")
                .withIgnorePaths("overdraftLimit")
                .withMatcher("accountID", exact())
                .withMatcher("id", exact());

        Example<Account> example = Example.of(account, matcher);
        return this.findOne(example);
    }
}