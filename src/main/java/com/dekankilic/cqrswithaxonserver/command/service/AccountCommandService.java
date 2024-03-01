package com.dekankilic.cqrswithaxonserver.command.service;

import com.dekankilic.cqrswithaxonserver.command.command.CreateAccountCommand;
import com.dekankilic.cqrswithaxonserver.command.command.DepositMoneyCommand;
import com.dekankilic.cqrswithaxonserver.command.command.WithdrawMoneyCommand;
import com.dekankilic.cqrswithaxonserver.command.dto.CreateAccountRequest;
import com.dekankilic.cqrswithaxonserver.command.dto.DepositRequest;
import com.dekankilic.cqrswithaxonserver.command.dto.WithdrawalRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountCommandService {
    private final CommandGateway commandGateway;

    public AccountCommandService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
    // it returns CompletableFuture, which means it will not have to wait for the command to fully execute
    // it just returns to us the accountId that we generated. Why does it return the accountId? That is how Axon implemented it internally.
    public CompletableFuture<String> createAccount(CreateAccountRequest createAccountRequest){
        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                createAccountRequest.getStartingBalance()
        ));
    }

    /******************************************************************************************************************/

    public CompletableFuture<String> depositToAccount(DepositRequest depositRequest){
        return commandGateway.send(new DepositMoneyCommand(
                depositRequest.getAccountId(),
                depositRequest.getAmount()
        ));
    }

    /******************************************************************************************************************/

    public CompletableFuture<String> withdrawFromAccount(WithdrawalRequest withdrawalRequest){
        return commandGateway.send(new WithdrawMoneyCommand(
                withdrawalRequest.getAccountId(),
                withdrawalRequest.getAmount()
        ));
    }
}
