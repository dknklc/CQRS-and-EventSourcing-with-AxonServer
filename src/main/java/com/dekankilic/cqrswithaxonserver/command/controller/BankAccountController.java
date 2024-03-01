package com.dekankilic.cqrswithaxonserver.command.controller;

import com.dekankilic.cqrswithaxonserver.command.dto.CreateAccountRequest;
import com.dekankilic.cqrswithaxonserver.command.dto.DepositRequest;
import com.dekankilic.cqrswithaxonserver.command.dto.WithdrawalRequest;
import com.dekankilic.cqrswithaxonserver.command.service.AccountCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/bank-account")
public class BankAccountController {

    private final AccountCommandService accountCommandService;

    public BankAccountController(AccountCommandService accountCommandService) {
        this.accountCommandService = accountCommandService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest request){
        try {
            CompletableFuture<String> response = accountCommandService.createAccount(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response.get());
        }catch (Exception e){
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositRequest request){
        try {
            CompletableFuture<String> response = accountCommandService.depositToAccount(request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Amount credited");
        }catch (Exception e){
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/withdraw")
    public ResponseEntity<String> deposit(@RequestBody WithdrawalRequest request){
        try {
            CompletableFuture<String> response = accountCommandService.withdrawFromAccount(request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Amount debited");
        }catch (Exception e){
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
