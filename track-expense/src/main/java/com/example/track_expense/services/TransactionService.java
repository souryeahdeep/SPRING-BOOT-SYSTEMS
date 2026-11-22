package com.example.track_expense.services;

import com.example.track_expense.dto.SaveTransaction;
import com.example.track_expense.dto.TransactionResponse;
import com.example.track_expense.dto.TransactionResponseWithCount;
import com.example.track_expense.enums.TransactionType;
import com.example.track_expense.models.Transaction;
import com.example.track_expense.models.User;
import com.example.track_expense.repository.TransactionRepo;
import com.example.track_expense.repository.UserRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    TransactionRepo  transactionRepo;
    UserRepo userRepo;
    public TransactionService(TransactionRepo  transactionRepo,UserRepo userRepo) {
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
    }
    public List<TransactionResponse> findAllByUserId(Long userId) {
        List<Transaction> transactions = transactionRepo.findAllByUserId(userId, Pageable.ofSize(5));
        List<TransactionResponse> responses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            responses.add(new TransactionResponse(transaction.getTransactionId(),transaction.getAmount(),transaction.getTransactionType(),transaction.getTimestamp()));
        }
        return responses;
    }

    public TransactionResponse saveTransaction(SaveTransaction transaction) {
        User user=userRepo.findById(transaction.userId()).get();
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.amount());
        newTransaction.setUser(user);
        newTransaction.setTransactionType(transaction.transactionType());
        newTransaction.setTimestamp(LocalDateTime.now());
        newTransaction = transactionRepo.save(newTransaction);

        return new TransactionResponse(newTransaction.getTransactionId(),newTransaction.getAmount(),newTransaction.getTransactionType(),newTransaction.getTimestamp());
    }

    public TransactionResponseWithCount findAllByTransactionType(Long userId,TransactionType transactionType) {
        List<Transaction> responses = transactionRepo.findAllByUserId(userId, Pageable.ofSize(10));
        int total = 0;
        List<TransactionResponse> responses1 = new ArrayList<>();
        for (Transaction transaction : responses) {
            if(transaction.getTransactionType().equals(transactionType)){
                total+=transaction.getAmount();
                responses1.add(new TransactionResponse(transaction.getTransactionId(),transaction.getAmount(),transaction.getTransactionType(),transaction.getTimestamp()));
            }
        }
        return new TransactionResponseWithCount(responses1,total);

    }




}
