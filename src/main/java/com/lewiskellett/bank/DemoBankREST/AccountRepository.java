package com.lewiskellett.bank.DemoBankREST;


import com.lewiskellett.bank.DemoBankREST.Types.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}