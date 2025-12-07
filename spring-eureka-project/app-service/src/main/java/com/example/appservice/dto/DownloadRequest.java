
package com.example.appservice.dto;

public class DownloadRequest {
    private String userName;
    private Long appId;

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getAppId() { return appId; }
    public void setAppId(Long appId) { this.appId = appId; }
}
