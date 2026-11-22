package com.example.track_expense.dto;

import java.util.List;

public record TransactionResponseWithCount(
        List<TransactionResponse> responses1,
        int total
) {
}
