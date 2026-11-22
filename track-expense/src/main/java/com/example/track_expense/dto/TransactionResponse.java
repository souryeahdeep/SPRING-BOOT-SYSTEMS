package com.example.track_expense.dto;

import com.example.track_expense.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(Long id, Integer amount, TransactionType transactionType, LocalDateTime timestamp) {}

