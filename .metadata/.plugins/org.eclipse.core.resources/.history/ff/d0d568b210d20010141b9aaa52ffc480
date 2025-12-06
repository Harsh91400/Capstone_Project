package com.example.appservice.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class AppDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appId;
    private String appName;
    @Column(length=2000)
    private String description;
    private LocalDate releaseDate;
    private String version;
    private Double rating;
    private String genre;
    private String appType;

    // relationship to AppOwner (optional)
    private Long appOwnerId;

    // getters and setters
    public Long getAppId() { return appId; }
    public void setAppId(Long appId) { this.appId = appId; }
    public String getAppName() { return appName; }
    public void setAppName(String appName) { this.appName = appName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getAppType() { return appType; }
    public void setAppType(String appType) { this.appType = appType; }
    public Long getAppOwnerId() { return appOwnerId; }
    public void setAppOwnerId(Long appOwnerId) { this.appOwnerId = appOwnerId; }
}
