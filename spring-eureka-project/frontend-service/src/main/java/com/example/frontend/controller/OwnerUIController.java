package com.example.frontend.controller;

import com.example.frontend.util.SessionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OwnerUIController {

    @Value("${backend.base.url}")
    private String backendBaseUrl;     // e.g. http://localhost:8081

    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // ========= LOGIN =========
    @GetMapping("/owners-ui/login")
    public String showOwnerLogin(@RequestParam(value = "error", required = false) String error,
                                 Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "owner-login";
    }

    @PostMapping("/owners-ui/login")
    public String doLogin(@RequestParam("userName") String userName,
                          @RequestParam("password") String password,
                          HttpSession session,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        String loginUrl = backendBaseUrl + "/owners/login";

        RestTemplate rt = restTemplate();

        // ab JSON body bhejenge (kyunki /owners/login @RequestBody expect kar raha hai)
        Map<String, String> body = new HashMap<>();
        body.put("userName", userName);
        body.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity =
                new HttpEntity<>(body, headers);

        try {
            // response JSON: { token, userName, role }
            ResponseEntity<Map> response =
                    rt.postForEntity(loginUrl, requestEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map respBody = response.getBody();
                String token = (String) respBody.get("token");
                String uName = (String) respBody.get("userName");
                String role  = (String) respBody.get("role");   // "OWNER"

                // userId abhi nahi hai, isliye null
                Integer ownerId = null;

                // SessionUtil me JWT + user store
                SessionUtil.setUser(session, role, ownerId, uName, token);

                redirectAttributes.addAttribute("userName", uName);
                return "redirect:/owners-ui/dashboard";
            } else if (response.getStatusCodeValue() == 401) {
                model.addAttribute("error", "Invalid username or password");
                return "owner-login";
            } else {
                model.addAttribute("error", "Login service unavailable");
                return "owner-login";
            }
        } catch (HttpStatusCodeException ex) {
            String bodyText = ex.getResponseBodyAsString();
            if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                model.addAttribute("error", "Invalid username or password");
            } else if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                model.addAttribute("error", "Owner not found");
            } else {
                model.addAttribute("error",
                        (bodyText != null && !bodyText.isBlank())
                                ? bodyText
                                : "Login failed: " + ex.getStatusCode());
            }
            return "owner-login";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "Something went wrong during login");
            return "owner-login";
        }
    }

    // ========= REGISTRATION =========
    @GetMapping("/owners-ui/register")
    public String showOwnerRegister(@RequestParam(value = "message", required = false) String message,
                                    @RequestParam(value = "error", required = false) String error,
                                    Model model) {
        if (message != null) model.addAttribute("message", message);
        if (error != null)   model.addAttribute("error", error);
        return "owner-register";
    }

    @PostMapping("/owners-ui/register")
    public String handleOwnerRegister(@RequestParam("firstName") String firstName,
                                      @RequestParam("lastName") String lastName,
                                      @RequestParam("userName") String userName,
                                      @RequestParam("password") String password,
                                      @RequestParam("email") String email,
                                      @RequestParam("mobile") String mobile,
                                      @RequestParam(value = "address1", required = false) String address1,
                                      @RequestParam(value = "address2", required = false) String address2,
                                      @RequestParam(value = "city",    required = false) String city,
                                      @RequestParam(value = "state",   required = false) String state,
                                      @RequestParam(value = "zipCode", required = false) String zipCode,
                                      @RequestParam(value = "country", required = false) String country,
                                      Model model) {

        String url = backendBaseUrl + "/owners/add";

        Map<String, String> body = new HashMap<>();
        body.put("firstName", firstName);
        body.put("lastName", lastName);
        body.put("userName", userName);
        body.put("password", password);
        body.put("email", email);
        body.put("mobile", mobile);
        body.put("address1", address1);
        body.put("address2", address2);
        body.put("city", city);
        body.put("state", state);
        body.put("zipCode", zipCode);
        body.put("country", country);

        RestTemplate rt = restTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity =
                new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response =
                    rt.postForEntity(url, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                model.addAttribute("message", "Owner registered successfully. Please login.");
            } else {
                model.addAttribute("error", "Failed to register owner.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "Something went wrong during registration.");
        }
        return "owner-register";
    }

    // ========= DASHBOARD =========
    @GetMapping("/owners-ui/dashboard")
    public String ownerDashboard(@RequestParam("userName") String userName,
                                 Model model,
                                 HttpSession session) {

        String sessionUser = SessionUtil.getUserName(session);
        String role = (String) session.getAttribute("role");

        if (sessionUser == null || !userName.equals(sessionUser) || !"OWNER".equals(role)) {
            return "redirect:/owners-ui/login";
        }

        model.addAttribute("ownerUserName", userName);
        return "owner-dashboard";
    }

    // ========= JSON ENDPOINT FOR APPS (dashboard JS) =========
    // GET /owners-ui/apps?userName=abhi123
    @GetMapping("/owners-ui/apps")
    @ResponseBody
    public ResponseEntity<String> getOwnerApps(@RequestParam("userName") String userName,
                                               HttpSession session) {

        String sessionUser = SessionUtil.getUserName(session);
        if (sessionUser == null || !userName.equals(sessionUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("[]");
        }

        String token = SessionUtil.getJwtToken(session);

        RestTemplate rt = restTemplate();
        String encodedUser = UriUtils.encode(userName, StandardCharsets.UTF_8);
        String url = backendBaseUrl + "/owners/" + encodedUser + "/apps";

        try {
            HttpHeaders headers = new HttpHeaders();
            if (token != null) {
                headers.set("Authorization", "Bearer " + token);
            }
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response =
                    rt.exchange(url, HttpMethod.GET, entity, String.class);

            return ResponseEntity
                    .status(response.getStatusCode())
                    .body(response.getBody());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("[]");
        }
    }

    // ========= ADD APP (OWNER) =========
    @GetMapping({"/owners-ui/add-app", "/owners-ui/add-owner-app"})
    public String showOwnerAddAppForm(HttpSession session) {
        String sessionUser = SessionUtil.getUserName(session);
        String role = (String) session.getAttribute("role");
        if (sessionUser == null || !"OWNER".equals(role)) {
            return "redirect:/owners-ui/login";
        }
        return "add-app";
    }

    @PostMapping("/owners-ui/add-app")
    public String handleOwnerAddApp(@RequestParam("appName") String appName,
                                    @RequestParam("appType") String appType,
                                    @RequestParam("description") String description,
                                    @RequestParam("genre") String genre,
                                    @RequestParam("rating") String rating,
                                    @RequestParam("releaseDate") String releaseDate,
                                    @RequestParam("version") String version,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {

        String ownerUserName = SessionUtil.getUserName(session);
        String role = (String) session.getAttribute("role");
        if (ownerUserName == null || !"OWNER".equals(role)) {
            return "redirect:/owners-ui/login";
        }

        String token = SessionUtil.getJwtToken(session);
        String url = backendBaseUrl + "/apps/add";

        RestTemplate rt = restTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("appName", appName);
        body.put("appType", appType);
        body.put("description", description);
        body.put("genre", genre);
        body.put("rating", rating);
        body.put("releaseDate", releaseDate);
        body.put("version", version);
        body.put("ownerUserName", ownerUserName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }

        HttpEntity<Map<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response =
                    rt.postForEntity(url, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                redirectAttributes.addAttribute("userName", ownerUserName);
                return "redirect:/owners-ui/dashboard";
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Failed to add app. Please try again.");
                return "redirect:/owners-ui/add-app";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("error",
                    "Something went wrong while adding app.");
            return "redirect:/owners-ui/add-app";
        }
    }

    // ========= LOGOUT =========
    @GetMapping("/owners-ui/logout")
    public String logout(HttpSession session) {
        SessionUtil.clear(session);
        return "redirect:/owners-ui/login";
    }
}
