package com.lewiskellett.bank.DemoBankREST.Assemblers;

import com.lewiskellett.bank.DemoBankREST.Controllers.AccountController;
import com.lewiskellett.bank.DemoBankREST.Controllers.TransactionController;
import com.lewiskellett.bank.DemoBankREST.Types.Account;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class AccountModelAssembler implements
        RepresentationModelAssembler<Account, EntityModel<Account>> {
    @Override
    public EntityModel<Account> toModel(Account account) {
        return EntityModel.of(account, //
                linkTo(methodOn(AccountController.class).one(account.getAccountID())).withSelfRel(),
                linkTo(methodOn(TransactionController.class).recent(account.getAccountID())).withRel("recentTransactions"));
    }
}