package com.example.track_expense;

import com.example.track_expense.dto.TransactionResponse;
import com.example.track_expense.enums.TransactionType;
import com.example.track_expense.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class TrackExpenseApplicationTests {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    TransactionService transactionService;


}
