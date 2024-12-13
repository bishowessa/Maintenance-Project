package com.lms.presentation;
import com.lms.persistence.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.lms.service.UserService;
import java.util.List;
import java.util.Optional;

import com.lms.persistence.User;
import com.lms.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        UserDetails currentUserDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByEmail(currentUserDetails.getUsername());

        if (currentUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(currentUser.get());
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }
}



