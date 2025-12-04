package com.example.appservice.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAppId;
    private Long userId;
    private Long appId;
    private LocalDate downloadDate;

    public Long getUserAppId() { return userAppId; }
    public void setUserAppId(Long userAppId) { this.userAppId = userAppId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getAppId() { return appId; }
    public void setAppId(Long appId) { this.appId = appId; }
    public LocalDate getDownloadDate() { return downloadDate; }
    public void setDownloadDate(LocalDate downloadDate) { this.downloadDate = downloadDate; }
}
