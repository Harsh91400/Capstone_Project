package com.example.appservice.service;

import com.example.appservice.repository.AppRepository;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    private final AppRepository appRepository;

    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public void deleteApp(Long id) {
        // pehle check karlo record exist karta hai ya nahi
        if (!appRepository.existsById(id)) {
            throw new RuntimeException("App not found with id: " + id);
        }

        appRepository.deleteById(id);
    }
}
