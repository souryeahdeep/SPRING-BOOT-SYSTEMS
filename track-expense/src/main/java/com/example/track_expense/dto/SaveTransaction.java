package com.example.track_expense.dto;

import com.example.track_expense.enums.TransactionType;

public record SaveTransaction(
        Integer amount,
        TransactionType transactionType,
        Long userId
) {

}
