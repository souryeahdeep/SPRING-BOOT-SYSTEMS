package com.example.track_expense.dto;

import java.math.BigDecimal;
import java.util.List;

public record TransactionResponseWithCount(
        List<TransactionResponse> responses1,
        BigDecimal total
) {
}
