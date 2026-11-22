package com.example.track_expense.repository;

import com.example.track_expense.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {

    List<Transaction> findAllByUserId(Long userId, Pageable pageable);

    List<Transaction> findAllByUserIdAndTimestampBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate,Pageable pageable);
}
