package com.dekankilic.cqrswithaxonserver.query.service;

import com.dekankilic.cqrswithaxonserver.common.event.AccountActivatedEvent;
import com.dekankilic.cqrswithaxonserver.common.event.AccountCreatedEvent;
import com.dekankilic.cqrswithaxonserver.common.event.AccountCreditedEvent;
import com.dekankilic.cqrswithaxonserver.common.event.AccountDebitedEvent;
import com.dekankilic.cqrswithaxonserver.query.model.Account;
import com.dekankilic.cqrswithaxonserver.query.query.FindAccountByIdQuery;
import com.dekankilic.cqrswithaxonserver.query.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManageAccountService {

    private final AccountRepository accountRepository;

    @EventHandler // to let Axon know that this method should be executed to handle the account created event
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("Handling AccountCreatedEvent...");

        Account account = new Account();
        account.setAccountId(accountCreatedEvent.getId());
        account.setBalance(accountCreatedEvent.getBalance());
        account.setStatus("CREATED");

        accountRepository.save(account);
    }

    /*****************************************************************************************************************/

    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        log.info("Handling AccountActivatedEvent...");

        Account account = accountRepository.findById(accountActivatedEvent.getId()).orElse(null);
        if(account != null){
            account.setStatus(accountActivatedEvent.getStatus());
            accountRepository.save(account);
        }
    }

    /*****************************************************************************************************************/

    @EventHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        log.info("Handling AccountCreditedEvent...");

        Account account = accountRepository.findById(accountCreditedEvent.getId()).orElse(null);
        if(account != null){
            account.setBalance(account.getBalance().add(accountCreditedEvent.getAmount()));
            accountRepository.save(account);
        }
    }

    /*****************************************************************************************************************/

    @EventHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        log.info("Handling AccountDebitedEvent...");

        Account account = accountRepository.findById(accountDebitedEvent.getId()).orElse(null);
        if(account != null){
            account.setBalance(account.getBalance().subtract(accountDebitedEvent.getAmount()));
            accountRepository.save(account);
        }
    }

    // What is left for us to do is to handle requests which we will receive through the RestApi that we will open for getting the account details.
    // Let's write the method that returns an Account and takes in a FindAccountByIdQuery.
    @QueryHandler // to let Axon know that this is the handler for FindAccountByIdQuery.
    public Account handle(FindAccountByIdQuery query){
        log.info("Handling FindAccountByIdQuery...");
        Account account = accountRepository.findById(query.getAccountId()).orElse(null);

        return account;
    }

}
