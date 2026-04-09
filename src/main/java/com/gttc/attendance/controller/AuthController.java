package com.gttc.attendance.controller;

import com.gttc.attendance.model.User;
import com.gttc.attendance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Error: Username and Password required!");
        }
        
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // In a production app, passwords should be encrypted.
            if (user.getPassword().equals(loginRequest.getPassword())) {
                if (loginRequest.getDepartment() != null && user.getDepartment() != null 
                    && !user.getDepartment().equalsIgnoreCase(loginRequest.getDepartment())) {
                    return ResponseEntity.status(401).body("Error: Department mismatch. You are accessing the wrong department.");
                }
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(401).body("Error: Invalid username, password, or department");
    }
}
