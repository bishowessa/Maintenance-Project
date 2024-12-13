package com.lms.persistence;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
//public class UserRepository {
//    private final List<User> users = new ArrayList<>();
//    private int idCounter = 1;
//
//
//    public User save(User user) {
//        if (user.getId() == null) {
//            user.setId(idCounter++);
//        }
//        users.add(user);
//        return user;
//    }
//
//    public Optional<User> findByEmail(String email) {
//        return users.stream()
//                .filter(user -> user.getEmail().equalsIgnoreCase(email))
//                .findFirst();
//    }
//
//    public List<User> findAll() {
//        return new ArrayList<>(users);
//    }
//}
@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public User save(User user) {
        users.add(user);
        return user;
    }

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }


    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}



