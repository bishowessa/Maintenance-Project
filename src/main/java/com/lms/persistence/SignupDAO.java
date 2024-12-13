package com.lms.persistence;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SignupDAO {

    private final List<SignupDTO> users = new ArrayList<>();

    // Save user details
    public void saveUser(SignupDTO user) {
        users.add(user);
    }

    // Find user by username
    public SignupDTO findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}

