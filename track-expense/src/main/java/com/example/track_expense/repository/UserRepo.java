package com.example.track_expense.repository;

import com.example.track_expense.models.Transaction;
import com.example.track_expense.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

}
