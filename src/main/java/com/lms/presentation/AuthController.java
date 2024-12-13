package com.lms.presentation;
import com.lms.persistence.SignupDAO;
import com.lms.persistence.SignupDTO;
import com.lms.persistence.User;
import com.lms.service.JwtUtil;
import com.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth") // Base path for authentication
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

//    @PostMapping("/login")
//    public String loginUser(@RequestBody User user) {
//        User existingUser = userService.getUserByUsername(user.getUsername());
//        if (existingUser == null) {
//            return "Error: User not found!";
//        }
//        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
//            return "Error: Invalid credentials!";
//        }
//
//        // Generate JWT Token
//        return jwtUtil.generateToken(existingUser.getUsername());
//    }
//    @PostMapping("/register")
//    public String registerUser(@RequestBody User user) {
//        if (userService.getUserByEmail(user.getEmail()) != null) {
//            return "Error: Email already exists!";
//        }
//        if (userService.getUserByUsername(user.getUsername()) != null) {
//            return "Error: Username already exists!";
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userService.saveUser(user);
//
//        return "User registered successfully!";
//    }


    // Login Endpoint
//    @PostMapping("/login")
//    public String loginUser(@RequestBody User user) {
//        // Find user by email
//        User existingUser = userService.getUserByEmail(user.getEmail());
//        if (existingUser == null) {
//            return "Error: User not found!";
//        }
//
//        // Verify the password
//        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
//            return "Error: Invalid credentials!";
//        }
//
//        return "Login successful!";
//    }
}

