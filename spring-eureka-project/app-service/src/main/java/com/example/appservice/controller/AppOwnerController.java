package com.example.appservice.controller;

import com.example.appservice.model.AppOwner;
import com.example.appservice.service.AppOwnerService;
import com.example.appservice.security.JwtUtil;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/owners")
public class AppOwnerController {

    private final AppOwnerService appOwnerService;
    private final EntityManager entityManager;
    private final JwtUtil jwtUtil;   // ✅ JWT util

    public AppOwnerController(AppOwnerService appOwnerService,
                              EntityManager entityManager,
                              JwtUtil jwtUtil) {
        this.appOwnerService = appOwnerService;
        this.entityManager = entityManager;
        this.jwtUtil = jwtUtil;
    }

    public static class OwnerAppDto {
        private Long id;
        private String name;
        private String type;
        private String genre;

        public OwnerAppDto() {}

        public OwnerAppDto(Long id, String name, String type, String genre) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.genre = genre;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getGenre() { return genre; }
        public void setGenre(String genre) { this.genre = genre; }
    }

    @PostMapping("/add")
    public ResponseEntity<AppOwner> addOwner(@RequestBody AppOwner owner) {
        AppOwner savedOwner = appOwnerService.saveOwner(owner);
        return ResponseEntity.ok(savedOwner);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppOwner>> getAllOwners() {
        return ResponseEntity.ok(appOwnerService.getAllOwners());
    }

    @GetMapping("/list")
    public ResponseEntity<List<AppOwner>> getOwnersList() {
        return ResponseEntity.ok(appOwnerService.getAllOwners());
    }

    // =============== OWNER LOGIN with JWT ===============
    @PostMapping("/login")
    public ResponseEntity<?> loginOwner(@RequestBody AppOwner payload) {
        try {
            String userName = payload.getUserName();
            String password = payload.getPassword();

            if (userName == null || password == null) {
                return ResponseEntity.badRequest().body("MISSING_CREDENTIALS");
            }

            AppOwner owner = appOwnerService.getOwnerByUsername(userName);
            if (owner == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("OWNER_NOT_FOUND");
            }

            if (!password.equals(owner.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("WRONG_PASSWORD");
            }

            // ✅ SUCCESS: generate JWT token for ROLE_OWNER
            String token = jwtUtil.generateToken(owner.getUserName(), "ROLE_OWNER");

            Map<String, Object> body = new HashMap<>();
            body.put("token", token);
            body.put("userName", owner.getUserName());
            body.put("role", "OWNER");

            return ResponseEntity.ok(body);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR: " + e.getMessage());
        }
    }

    @GetMapping("/{username}/apps")
    public ResponseEntity<List<OwnerAppDto>> getAppsByOwner(@PathVariable String username) {
        AppOwner owner = appOwnerService.getOwnerByUsername(username);

        if (owner == null) {
            return ResponseEntity.notFound().build();
        }

        String sql = """
            SELECT APP_ID, APP_NAME, APP_TYPE, GENRE
            FROM APP_DETAILS
            WHERE APP_OWNER_ID = :id
            """;

        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager
                .createNativeQuery(sql)
                .setParameter("id", owner.getAppOwnerId())
                .getResultList();

        List<OwnerAppDto> apps = rows.stream()
                .map(r -> new OwnerAppDto(
                        r[0] == null ? null : ((Number) r[0]).longValue(),
                        r[1] == null ? null : r[1].toString(),
                        r[2] == null ? null : r[2].toString(),
                        r[3] == null ? null : r[3].toString()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(apps);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        try {
            appOwnerService.deleteOwner(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
