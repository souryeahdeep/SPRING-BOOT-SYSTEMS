package com.example.track_expense.controller;

import com.example.track_expense.dto.SaveTransaction;
import com.example.track_expense.dto.TransactionResponse;
import com.example.track_expense.dto.TransactionResponseWithCount;
import com.example.track_expense.enums.TransactionType;
import com.example.track_expense.models.Transaction;
import com.example.track_expense.services.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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


}
