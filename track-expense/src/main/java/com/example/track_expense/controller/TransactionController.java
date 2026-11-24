package com.example.track_expense.controller;

import com.example.track_expense.exceptions.UserDoesNotExists;
import com.example.track_expense.exceptions.WrongTimePeriodException;
import com.example.track_expense.dto.SaveTransaction;
import com.example.track_expense.dto.TransactionResponse;
import com.example.track_expense.dto.TransactionResponseWithCount;
import com.example.track_expense.enums.TransactionType;
import com.example.track_expense.services.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    TransactionService transactionService;
    TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /// FETCH TRANSACTIONS BY DEBIT OR CREDIT
    @GetMapping
    public TransactionResponseWithCount findAllByTransactionType(@RequestParam Long userId, @RequestParam TransactionType transactionType){
        return transactionService.findAllByTransactionType(userId,transactionType);
    }

    /// SAVE TRANSACTION
    @PostMapping
    public TransactionResponse save(@RequestBody SaveTransaction transaction){
        return transactionService.saveTransaction(transaction);
    }
    @GetMapping("/{userId}/transactions")
    public TransactionResponseWithCount findAll(@PathVariable Long userId){
        return transactionService.fetchAllTransactions(userId);
    }
    /// FETCH TRANSACTIONS IN A TIME PERIOD. CAN BE USED FOR FETCHING MONTHLY TRANSACTION.
    @GetMapping("/{userId}/transactions-in-period")
    public TransactionResponseWithCount findAllForTimePeriod(@PathVariable Long userId,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws WrongTimePeriodException, UserDoesNotExists {
        return transactionService.findAllByTimePeriod(userId,start,end);
    }



}
