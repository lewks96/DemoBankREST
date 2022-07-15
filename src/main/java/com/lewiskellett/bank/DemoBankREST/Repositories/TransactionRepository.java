package com.lewiskellett.bank.DemoBankREST.Repositories;

import com.lewiskellett.bank.DemoBankREST.Types.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}