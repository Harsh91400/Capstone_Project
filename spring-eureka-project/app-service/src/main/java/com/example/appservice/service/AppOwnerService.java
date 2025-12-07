package com.example.appservice.service;

import com.example.appservice.model.AppOwner;
import com.example.appservice.repository.AppOwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppOwnerService {

    private final AppOwnerRepository appOwnerRepository;

    public AppOwnerService(AppOwnerRepository appOwnerRepository) {
        this.appOwnerRepository = appOwnerRepository;
    }

    public AppOwner getOwnerByUsername(String username) {
        return appOwnerRepository.findByUserName(username);
    }

    public AppOwner saveOwner(AppOwner owner) {
        return appOwnerRepository.save(owner);
    }

    public List<AppOwner> getAllOwners() {
        return appOwnerRepository.findAll();
    }

    public void deleteOwner(Long id) {
        // Agar id exist hi nahi karti to exception throw kar do
        if (!appOwnerRepository.existsById(id)) {
            throw new RuntimeException("Owner not found with id: " + id);
        }
        appOwnerRepository.deleteOwnerById(id);
    }
}
