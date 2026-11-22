package com.example.track_expense.services;

import com.example.track_expense.dto.TransactionResponse;
import com.example.track_expense.dto.TransactionResponseWithCount;
import com.example.track_expense.dto.UserDto;
import com.example.track_expense.dto.UserResponse;
import com.example.track_expense.models.Transaction;
import com.example.track_expense.models.User;
import com.example.track_expense.repository.UserRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    public UserResponse createUser(UserDto userDto) {
        User user=new User();
        user.setName(userDto.name());
        user =userRepo.save(user);
        return new UserResponse(user.getId(), user.getName());
    }

    @Transactional(readOnly=true)
    public List<UserResponse> findAllUsers() {
        List<User> users=userRepo.findAll();
        List<UserResponse> userResponseList=new ArrayList<>();
        for(User user:users){
            userResponseList.add(new UserResponse(user.getId(),user.getName()));
        }
        return userResponseList;
    }

    @Transactional(readOnly=true)
    public TransactionResponseWithCount fetchAllTransactions(Long userId) {
        Optional<User> user=userRepo.findById(userId);
        List<TransactionResponse> transactionResponseList=new ArrayList<>();
        int total=0;
        if(user.isPresent()){
            for(Transaction transaction:user.get().getTransactions()){
                total+=transaction.getAmount();
                transactionResponseList.add(new TransactionResponse(transaction.getTransactionId(),transaction.getAmount(),transaction.getTransactionType(),transaction.getTimestamp()));
            }
        }
        return new TransactionResponseWithCount(transactionResponseList,total);

    }

    public TransactionResponseWithCount findAllByTimePeriod(Long userId, LocalDateTime start, LocalDateTime end) {
        List<Transaction> responses = userRepo.findById(userId).get().getTransactions();
        List<TransactionResponse> responses1 = new ArrayList<>();
        int total = 0;
        for (Transaction transaction : responses) {
            total+=transaction.getAmount();
            if(transaction.getTimestamp().isAfter(start) && transaction.getTimestamp().isBefore(end)){
                responses1.add(new TransactionResponse(transaction.getTransactionId(),transaction.getAmount(),transaction.getTransactionType(),transaction.getTimestamp()));
            }
        }
        return new TransactionResponseWithCount(responses1,total);
    }

}
