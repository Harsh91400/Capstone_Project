package com.example.appservice.controller;

import com.example.appservice.model.User;

import com.example.appservice.model.UserActivationMailRequest;
import com.example.appservice.repository.UserRepository;
import com.github.andrewoma.dexx.collection.HashMap;
import com.github.andrewoma.dexx.collection.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.appservice.security.JwtUtil;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3001")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private JwtUtil jwtUtil;

    // --------- NEW USER REGISTRATION ----------
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try {
            if(user.getDateOfOpen() == null) {
                user.setDateOfOpen(LocalDate.now());
            }
            user.setStatus("INACTIVE");
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding user: " + e.getMessage());
        }
    }

    // --------- LOGIN ----------
 // --------- LOGIN ----------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User payload) {
        try {
            if (payload.getUserName() == null || payload.getPassword() == null) {
                return ResponseEntity.badRequest().body("MISSING_CREDENTIALS");
            }

            Optional<User> userOpt = userRepository.findByUserName(payload.getUserName());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("USER_NOT_FOUND");
            }

            User user = userOpt.get();

            if (!payload.getPassword().equals(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("WRONG_PASSWORD");
            }

            if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Your account is INACTIVE. Please contact admin.");
            }

            // ✅ SUCCESS: generate JWT token for ROLE_USER
            String token = jwtUtil.generateToken(user.getUserName(), "ROLE_USER");

            // JSON response: { "token": "...", "userName": "...", "role": "USER" }
            Map<String, Object> body = new HashMap<>();
            body.put("token", token);
            body.put("userName", user.getUserName());
            body.put("role", "USER");

            return ResponseEntity.ok(body);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR: " + e.getMessage());
        }
    }


    // --------- ALL USERS ----------
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // --------- USER BY ID ----------
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + id);
        }
    }

    // --------- DELETE USER ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --------- ADMIN: ACTIVATE USER ----------
    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + id);
        }
        
        User user = optionalUser.get();
        user.setStatus("ACTIVE");
        User updated = userRepository.save(user);
        
        // ✅ Mail sending code
        UserActivationMailRequest req = new UserActivationMailRequest();
        req.setEmail(user.getEmail());
        req.setFirstName(user.getFirstName());
        req.setUserName(user.getUserName());
        
        String url = "http://localhost:8082/mail/user-activated";
        
        try {
            ResponseEntity<String> resp = restTemplate.postForEntity(url, req, String.class);
            System.out.println("Mail sent successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return ResponseEntity.ok(updated);
    }
}