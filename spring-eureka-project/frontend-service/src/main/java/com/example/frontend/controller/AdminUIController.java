package com.example.frontend.controller;

import com.example.frontend.util.SessionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admins-ui")
public class AdminUIController {

    private final RestTemplate restTemplate;
    private final String backendBase;

    public AdminUIController(RestTemplate restTemplate,
                             @Value("${backend.base.url}") String backendBase) {
        this.restTemplate = restTemplate;
        this.backendBase = backendBase;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String userName,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        try {
            userName = userName.trim();
            password = password.trim();

            // ---- Request body ----
            Map<String, String> body = new HashMap<>();
            body.put("userName", userName);
            body.put("password", password);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            // app-service /admins/login -> JSON: { token, userName, role }
            ResponseEntity<Map> resp =
                    restTemplate.postForEntity(backendBase + "/admins/login",
                            entity, Map.class);

            if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
                Map respBody = resp.getBody();
                String token = (String) respBody.get("token");
                String uName = (String) respBody.get("userName");
                String role  = (String) respBody.get("role");   // "ADMIN"

                Integer adminId = null; // agar future me backend se id bhejo to yahan set kar sakte ho

                // JWT + session data save
                SessionUtil.setUser(session, role, adminId, uName, token);

                return "redirect:/admins-ui/dashboard";
            }

            model.addAttribute("message", "Unexpected response: " + resp.getBody());
        } catch (HttpStatusCodeException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute("message", "Admin doesn't exist");
            } else if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                model.addAttribute("message", "Wrong password");
            } else if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                model.addAttribute("message", "Please enter username and password");
            } else {
                model.addAttribute("message",
                        "Login failed: " + ex.getStatusCode().value());
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "admin-login";
    }

    // =============== Helper: Authorization header ===============
    private HttpEntity<Void> buildAuthEntity(HttpSession session) {
        String token = SessionUtil.getJwtToken(session);
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }
        return new HttpEntity<>(headers);
    }

    // =============== USERS LIST PAGE ===============
    @GetMapping("/users")
    public String showUsers(Model model, HttpSession session) {
        try {
            HttpEntity<Void> entity = buildAuthEntity(session);

            ResponseEntity<List> resp = restTemplate.exchange(
                    backendBase + "/users/all",
                    HttpMethod.GET,
                    entity,
                    List.class
            );
            model.addAttribute("users", resp.getBody());
        } catch(Exception e) {
            model.addAttribute("users", List.of());
            model.addAttribute("error", "Cannot load users: " + e.getMessage());
        }
        return "users";
    }

    // =============== ADMIN DASHBOARD ===============
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        try {
            HttpEntity<Void> entity = buildAuthEntity(session);

            // USERS
            ResponseEntity<List> usersResp =
                    restTemplate.exchange(backendBase + "/users/all",
                            HttpMethod.GET, entity, List.class);
            model.addAttribute("users", usersResp.getBody());

            // APPS
            ResponseEntity<List> appsResp =
                    restTemplate.exchange(backendBase + "/apps",
                            HttpMethod.GET, entity, List.class);
            model.addAttribute("apps", appsResp.getBody());

            // OWNERS
            ResponseEntity<List> ownersResp =
                    restTemplate.exchange(backendBase + "/owners/list",
                            HttpMethod.GET, entity, List.class);
            model.addAttribute("owners", ownersResp.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to load data: " + e.getMessage());
        }

        return "admin-dashboard";
    }

    // =============== DELETE USER ===============
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id,
                             HttpSession session,
                             RedirectAttributes ra) {
        try {
            HttpEntity<Void> entity = buildAuthEntity(session);

            restTemplate.exchange(
                    backendBase + "/users/" + id,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
            ra.addFlashAttribute("message", "User deleted: " + id);
        } catch (Exception e) {
            ra.addFlashAttribute("message", "Failed to delete user: " + e.getMessage());
        }
        return "redirect:/admins-ui/dashboard";
    }

    // =============== ACTIVATE USER ===============
    @PostMapping("/users/{id}/activate")
    public String activateUser(@PathVariable("id") Long id,
                               HttpSession session,
                               RedirectAttributes ra) {
        try {
            HttpEntity<Void> entity = buildAuthEntity(session);

            restTemplate.exchange(
                    backendBase + "/users/" + id + "/activate",
                    HttpMethod.PUT,
                    entity,
                    Void.class
            );
            ra.addFlashAttribute("message", "User activated: " + id);
        } catch (Exception e) {
            ra.addFlashAttribute("message", "Failed to activate user: " + e.getMessage());
        }
        return "redirect:/admins-ui/dashboard";
    }

    // =============== DELETE APP ===============
    @PostMapping("/apps/{id}/delete")
    public String deleteApp(@PathVariable("id") Long id,
                            HttpSession session,
                            RedirectAttributes ra) {
        try {
            HttpEntity<Void> entity = buildAuthEntity(session);

            restTemplate.exchange(
                    backendBase + "/apps/" + id,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
            ra.addFlashAttribute("message", "App deleted: " + id);
        } catch (Exception e) {
            ra.addFlashAttribute("message", "Failed to delete app: " + e.getMessage());
        }
        return "redirect:/admins-ui/dashboard";
    }

    // =============== DELETE OWNER ===============
    @PostMapping("/owners/{id}/delete")
    public String deleteOwner(@PathVariable("id") Long id,
                              HttpSession session,
                              RedirectAttributes ra) {
        try {
            HttpEntity<Void> entity = buildAuthEntity(session);

            restTemplate.exchange(
                    backendBase + "/owners/" + id,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
            ra.addFlashAttribute("message", "Owner deleted: " + id);
        } catch (Exception e) {
            ra.addFlashAttribute("message", "Failed to delete owner: " + e.getMessage());
        }
        return "redirect:/admins-ui/dashboard";
    }

}
