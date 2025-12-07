package com.example.appservice.controller;

import com.example.appservice.model.AppDetails;
import com.example.appservice.model.UserApp;
import com.example.appservice.repository.AppDetailsRepository;
import com.example.appservice.repository.UserAppRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3002")
public class UserDashboardController {

    private final AppDetailsRepository appDetailsRepository;
    private final UserAppRepository userAppRepository;

    public UserDashboardController(AppDetailsRepository appDetailsRepository,
                                   UserAppRepository userAppRepository) {
        this.appDetailsRepository = appDetailsRepository;
        this.userAppRepository = userAppRepository;
    }

    @GetMapping("/{userId}/apps")
    public List<AppDetails> getAllApps(@PathVariable Long userId) {
        return appDetailsRepository.findAll();
    }

    @PostMapping("/{userId}/download/{appId}")
    public ResponseEntity<UserApp> downloadApp(@PathVariable Long userId,
                                               @PathVariable Long appId) {

        if (userAppRepository.existsByUserIdAndAppId(userId, appId)) {
            return ResponseEntity.ok().build();   
        }

        UserApp userApp = new UserApp();
        userApp.setUserId(userId);
        userApp.setAppId(appId);
        userApp.setDownloadDate(LocalDate.now());

        UserApp saved = userAppRepository.save(userApp);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{userId}/downloads")
    public List<UserApp> getDownloadedApps(@PathVariable Long userId) {
        return userAppRepository.findByUserId(userId);
    }
}
