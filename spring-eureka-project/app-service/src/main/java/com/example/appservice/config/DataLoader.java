package com.example.appservice.config;

import com.example.appservice.model.Admin;
import com.example.appservice.model.AppDetails;
import com.example.appservice.repository.AdminRepository;
import com.example.appservice.repository.AppDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final AdminRepository adminRepo;
    private final AppDetailsRepository appRepo;

    public DataLoader(AdminRepository adminRepo, AppDetailsRepository appRepo){
        this.adminRepo = adminRepo;
        this.appRepo = appRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if(adminRepo.count()==0){
            Admin a = new Admin();
            a.setAdminName("Super Admin");
            a.setUserName("admin");
            a.setPassword("admin");
            a.setEmail("admin@example.com");
            a.setAdminStatus("YES");
            adminRepo.save(a);
        }
        if(appRepo.count()==0){
            AppDetails ad = new AppDetails();
            ad.setAppName("PhotoEditor");
            ad.setDescription("Simple photo editor");
            ad.setReleaseDate(LocalDate.of(2023,1,1));
            ad.setVersion("1.0");
            ad.setRating(4.2);
            ad.setGenre("Tools");
            ad.setAppType("Free");
            appRepo.save(ad);

            AppDetails ad2 = new AppDetails();
            ad2.setAppName("SpaceShooter");
            ad2.setDescription("Arcade game");
            ad2.setReleaseDate(LocalDate.of(2024,5,10));
            ad2.setVersion("2.1");
            ad2.setRating(4.7);
            ad2.setGenre("Games");
            ad2.setAppType("Paid");
            appRepo.save(ad2);
        }
    }
}
