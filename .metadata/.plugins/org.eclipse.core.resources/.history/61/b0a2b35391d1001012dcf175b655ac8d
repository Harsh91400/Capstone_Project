package com.example.frontend.controller;

import com.example.frontend.util.SessionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users-ui")
public class UserUIController {

    private final RestTemplate restTemplate;
    private final String backendBase;

    public UserUIController(RestTemplate restTemplate, @Value("${backend.base.url}") String backendBase) {
        this.restTemplate = restTemplate;
        this.backendBase = backendBase;
    }

    @GetMapping("/register")
    public String showRegister() {
        return "user-register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam Map<String,String> params, Model model) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String json = String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"userName\":\"%s\",\"password\":\"%s\",\"email\":\"%s\",\"mobileNo\":\"%s\"}", params.getOrDefault("firstName",""), params.getOrDefault("lastName",""), params.getOrDefault("userName",""), params.getOrDefault("password",""), params.getOrDefault("email",""), params.getOrDefault("mobileNo",""));
            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            ResponseEntity<String> resp = restTemplate.postForEntity(backendBase + "/users/add", entity, String.class);
            model.addAttribute("message", "Registration response: " + resp.getBody());
        } catch(Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "user-register";
    }

    @GetMapping("/login")
    public String showLogin() { return "user-login"; }

    @PostMapping("/login")
    public String doLogin(@RequestParam String userName, @RequestParam String password, HttpSession session, Model model) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String json = String.format("{\"userName\":\"%s\",\"password\":\"%s\"}", userName, password);
            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            ResponseEntity<String> resp = restTemplate.postForEntity(backendBase + "/users/login", entity, String.class);
            if(resp.getStatusCode().is2xxSuccessful()) {
                Integer userId = null;
                SessionUtil.setUser(session, "USER", userId, userName);
                model.addAttribute("message", "Login successful");
            } else {
                model.addAttribute("message", "Login failed: " + resp.getBody());
            }
        } catch(Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "user-login";
    }

    @GetMapping("/all")
    public String showAllUsers(Model model, HttpSession session) {
        try {
            ResponseEntity<List> resp = restTemplate.exchange(backendBase + "/users/all", HttpMethod.GET, null, List.class);
            model.addAttribute("users", resp.getBody());
        } catch(Exception e) {
            model.addAttribute("users", List.of());
            model.addAttribute("error", "Cannot load users: " + e.getMessage());
        }
        return "users";
    }

    @GetMapping("/{id}")
    public String userById(@PathVariable("id") Integer id, Model model) {
        try {
            ResponseEntity<Map> resp = restTemplate.getForEntity(backendBase + "/users/" + id, Map.class);
            model.addAttribute("user", resp.getBody());
        } catch(Exception e) {
            model.addAttribute("error", "Cannot load user: " + e.getMessage());
        }
        return "user-detail";
    }
}
