package com.example.track_expense.dto;

import com.example.track_expense.enums.TransactionType;

import java.math.BigDecimal;

public record SaveTransaction(
        BigDecimal amount,
        TransactionType transactionType,
        Long userId
) {

}
