package com.example.appservice.controller;

import com.example.appservice.model.AppDetails;
import com.example.appservice.model.AppOwner;
import com.example.appservice.repository.AppDetailsRepository;
import com.example.appservice.service.AppOwnerService;
import com.example.appservice.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apps")
public class AppController {

    @Autowired
    private AppDetailsRepository appDetailsRepository;

    @Autowired
    private AppService appService;

    @Autowired
    private AppOwnerService appOwnerService;

    
    @PostMapping("/add")
    public AppDetails addApp(@RequestBody Map<String, Object> payload) {

        AppDetails app = new AppDetails();

        // --- basic fields ---
        app.setAppName((String) payload.get("appName"));
        app.setAppType((String) payload.get("appType"));
        app.setDescription((String) payload.get("description"));
        app.setGenre((String) payload.get("genre"));

        Object ratingObj = payload.get("rating");
        if (ratingObj != null) {
            try {
                app.setRating(Double.parseDouble(ratingObj.toString()));
            } catch (Exception ignored) {
            }
        }

        Object versionObj = payload.get("version");
        if (versionObj != null) {
            app.setVersion(versionObj.toString());
        }

        Object releaseDateObj = payload.get("releaseDate");
        if (releaseDateObj != null) {
            try {
                app.setReleaseDate(LocalDate.parse(releaseDateObj.toString()));
            } catch (Exception ignored) {

            }
        }

        // --- APP_OWNER_ID set ---

        Integer appOwnerId = null;

        Object ownerIdObj = payload.get("appOwnerId");
        if (ownerIdObj != null) {
            try {
                appOwnerId = Integer.valueOf(ownerIdObj.toString());
            } catch (Exception ignored) {
            }
        }

        if (appOwnerId == null) {
            Object ownerUserNameObj = payload.get("ownerUserName");
            if (ownerUserNameObj != null) {
                String ownerUserName = ownerUserNameObj.toString();
                AppOwner owner = appOwnerService.getOwnerByUsername(ownerUserName);
                if (owner != null) {
                    appOwnerId = owner.getAppOwnerId();
                }
            }
        }

        app.setAppOwnerId(appOwnerId); 

        // DB me save
        return appDetailsRepository.save(app);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApp(@PathVariable Long id) {
        appService.deleteApp(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<AppDetails> getAll() {
        return appDetailsRepository.findAll();
    }

    @GetMapping("/genre/{genre}")
    public List<AppDetails> getByGenre(@PathVariable String genre) {
        return appDetailsRepository.findByGenreIgnoreCase(genre);
    }
}
