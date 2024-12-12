package com.lms.service;

import com.lms.persistence.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public List<User> getUsers(){
        return List.of(
                new User("12345", "John", "Doe", "john.doe@example.com", "securePassword123")
        );
    }
}
