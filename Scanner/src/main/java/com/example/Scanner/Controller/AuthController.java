package com.example.Scanner.Controller;

import com.example.Scanner.Model.User;
import com.example.Scanner.Repository.UserRepository;
import com.example.Scanner.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = authService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest) {
        User authenticatedUser = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(authenticatedUser);
    }
}
