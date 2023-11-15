package com.ke.task.controllers;

import com.ke.task.entities.UserEntity;
import com.ke.task.jsonClasses.AddUserResponse;
import com.ke.task.jsonClasses.UserResponse;
import com.ke.task.services.UserService;
import com.ke.task.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<AddUserResponse> createUser(@RequestBody UserEntity userEntity) throws SQLException {
        UserEntity user = userService.addUser(userEntity);
        return ResponseEntity.ok().body(new AddUserResponse(user.getId(), JwtUtils.generateAccessToken(user.getEmail())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id, @RequestHeader("Authorization") String token) {
        String email = JwtUtils.extractUserEmail(token);
        UserEntity user = userService.getUser(id, email);
        return ResponseEntity.ok().body(new UserResponse(user));
    }

}
