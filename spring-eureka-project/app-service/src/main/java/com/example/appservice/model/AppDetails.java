package com.example.appservice.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "APP_DETAILS")
public class AppDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APP_ID")
    private Long appId;

    @Column(name = "APP_NAME")
    private String appName;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "RELEASE_DATE")
    private LocalDate releaseDate;

    @Column(name = "VERSION")
    private String version;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "GENRE")
    private String genre;

    @Column(name = "APP_TYPE")
    private String appType;

    // APP_OWNER.APP_OWNER_ID se link
    @Column(name = "APP_OWNER_ID")
    private Integer appOwnerId;

    // ===== getters / setters =====

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public Integer getAppOwnerId() {
        return appOwnerId;
    }

    public void setAppOwnerId(Integer appOwnerId) {
        this.appOwnerId = appOwnerId;
    }
}
