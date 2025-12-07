package com.example.appservice.repository;

import com.example.appservice.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    List<UserApp> findByUserId(Long userId);
    boolean existsByUserIdAndAppId(Long userId, Long appId);
}