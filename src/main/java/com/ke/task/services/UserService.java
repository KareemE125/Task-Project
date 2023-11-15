package com.ke.task.services;

import com.ke.task.entities.UserEntity;
import com.ke.task.repositories.UserRepository;
import com.ke.task.utils.CryptoUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity addUser(UserEntity user) throws SQLException {
        // Check if the user already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new SQLException("User with email: \"" + user.getEmail() + "\" already exists");
        }
        // Generate ID using SHA-1 based on the user's email
        user.setId(CryptoUtils.generateSHA1Value(user.getEmail()));
        user = userRepository.save(user);
        return user;
    }

    public UserEntity getUser(String id, String email) {
        return userRepository.findByIdAndEmail(id, email)
                .orElseThrow(() -> new EntityNotFoundException("User with ID: " + id + " is not found"));
    }

}
