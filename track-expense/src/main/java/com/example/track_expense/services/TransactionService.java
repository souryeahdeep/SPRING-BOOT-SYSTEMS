package com.example.track_expense.services;

import com.example.track_expense.exceptions.UserDoesNotExists;
import com.example.track_expense.exceptions.WrongTimePeriodException;
import com.example.track_expense.common.CONSTANTS;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepo  transactionRepo;
    private final UserRepo userRepo;
    public TransactionService(TransactionRepo  transactionRepo,UserRepo userRepo) {
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
    }

    @Transactional(readOnly=true)
    public List<TransactionResponse> findAllByUserId(Long userId) throws UserDoesNotExists {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new UserDoesNotExists("User doesn't exists");
        }
        List<Transaction> transactions = user.get().getTransactions();
        List<TransactionResponse> responses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            responses.add(new TransactionResponse(transaction.getTransactionId(),transaction.getAmount(),transaction.getTransactionType(),transaction.getTimestamp()));
        }
        return responses;
    }

    public TransactionResponse saveTransaction(SaveTransaction transaction) {
        Optional<User> user = userRepo.findById(transaction.userId());
        if(user.isEmpty()){
            throw new UserDoesNotExists("User doesn't exists");
        }
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.amount());
        newTransaction.setUser(user.get());
        newTransaction.setTransactionType(transaction.transactionType());
        newTransaction.setTimestamp(LocalDateTime.now());
        newTransaction = transactionRepo.save(newTransaction);

        return new TransactionResponse(newTransaction.getTransactionId(),newTransaction.getAmount(),newTransaction.getTransactionType(),newTransaction.getTimestamp());
    }

    @Transactional(readOnly=true)
    public TransactionResponseWithCount findAllByTransactionType(Long userId,TransactionType transactionType) {
        List<Transaction> responses = transactionRepo.findAllByUserId(userId, Pageable.ofSize(CONSTANTS.PAGE_SIZE));
        BigDecimal total = BigDecimal.valueOf(0);
        List<TransactionResponse> responses1 = new ArrayList<>();
        for (Transaction transaction : responses) {
            if(transaction.getTransactionType().equals(transactionType)){
                total = total.add(transaction.getAmount());
                responses1.add(new TransactionResponse(transaction.getTransactionId(),transaction.getAmount(),transaction.getTransactionType(),transaction.getTimestamp()));
            }
        }
        return new TransactionResponseWithCount(responses1,total);

    }

    @Transactional(readOnly=true)
    public TransactionResponseWithCount fetchAllTransactions(Long userId) {
        Optional<User> user=userRepo.findById(userId);
        List<TransactionResponse> transactionResponseList=new ArrayList<>();
        BigDecimal total=new BigDecimal(0);
        if(user.isPresent()){
            for(Transaction transaction:user.get().getTransactions()){
                total=total.add(transaction.getAmount());
                transactionResponseList.add(new TransactionResponse(transaction.getTransactionId(),transaction.getAmount(),transaction.getTransactionType(),transaction.getTimestamp()));
            }
        }
        return new TransactionResponseWithCount(transactionResponseList,total);

    }

    @Transactional(readOnly=true)
    public TransactionResponseWithCount findAllByTimePeriod(Long userId, LocalDateTime start, LocalDateTime end) throws WrongTimePeriodException,UserDoesNotExists {
        if(start.isAfter(end)){
            throw new WrongTimePeriodException("Start time is after end time");
        }
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new UserDoesNotExists("User doesn't exists");
        }
        List<Transaction> transactions = user.get().getTransactions();
        List<TransactionResponse> responses1 = new ArrayList<>();
        BigDecimal total = new BigDecimal(0);
        for (Transaction transaction : transactions) {
            if(transaction.getTimestamp().isAfter(start) && transaction.getTimestamp().isBefore(end)){
                total = total.add(transaction.getAmount());
                responses1.add(new TransactionResponse(transaction.getTransactionId(),transaction.getAmount(),transaction.getTransactionType(),transaction.getTimestamp()));
            }
        }
        return new TransactionResponseWithCount(responses1,total);
    }

}
