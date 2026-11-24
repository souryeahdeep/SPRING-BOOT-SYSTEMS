package com.example.track_expense.services;

import com.example.track_expense.dto.UserDto;
import com.example.track_expense.dto.UserResponse;
import com.example.track_expense.models.User;
import com.example.track_expense.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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




}
