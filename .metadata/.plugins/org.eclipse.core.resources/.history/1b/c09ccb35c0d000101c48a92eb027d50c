package com.example.appservice.controller;

import com.example.appservice.model.Admin;
import com.example.appservice.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/add")
    public Admin addAdmin(@RequestBody Admin admin){
        admin.setAdminStatus("YES");
        return adminRepository.save(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Admin payload){
        try {
            if(payload.getUserName() == null || payload.getPassword() == null){
                return ResponseEntity.badRequest().body("Missing username or password");
            }

            Optional<Admin> adminOpt = adminRepository.findByUserName(payload.getUserName());
            if(adminOpt.isPresent() && payload.getPassword().equals(adminOpt.get().getPassword())){
                return ResponseEntity.ok("OK: Admin logged in");
            } else {
                return ResponseEntity.status(401).body("FAIL: invalid credentials");
            }
        } catch (Exception e){
            e.printStackTrace();  // see actual error in console
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

}
