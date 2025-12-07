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
@RequestMapping("/users-ui")
public class UserUIController {

    private final RestTemplate restTemplate;
    private final String backendBase;

    public UserUIController(RestTemplate restTemplate,
                            @Value("${backend.base.url}") String backendBase) {
        this.restTemplate = restTemplate;
        this.backendBase = backendBase;  // e.g. http://localhost:8081
    }

    // =============== REGISTER (jo pehle tha, wahi rakha hai) ===============
    @GetMapping("/register")
    public String showRegister() {
        return "user-register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam Map<String,String> params, Model model) {
        try {
            Map<String, Object> body = new HashMap<>();

            body.put("firstName",  params.getOrDefault("firstName", ""));
            body.put("lastName",   params.getOrDefault("lastName", ""));
            body.put("userName",   params.getOrDefault("userName", ""));
            body.put("password",   params.getOrDefault("password", ""));
            body.put("email",      params.getOrDefault("email", ""));
            body.put("mobileNo",   params.getOrDefault("mobileNo", ""));

            body.put("dob", params.getOrDefault("dob", null));

            body.put("accountType", params.getOrDefault("accountType", null));
            body.put("cheqFacil",   params.getOrDefault("cheqFacil", null));

            String amountStr = params.get("amount");
            if (amountStr != null && !amountStr.isBlank()) {
                try {
                    body.put("amount", Double.parseDouble(amountStr));
                } catch (NumberFormatException e) {
                    body.put("amount", null);
                }
            } else {
                body.put("amount", null);
            }

            body.put("address1", params.getOrDefault("address1", null));
            body.put("address2", params.getOrDefault("address2", null));
            body.put("city",     params.getOrDefault("city", null));
            body.put("state",    params.getOrDefault("state", null));
            body.put("zipCode",  params.getOrDefault("zipCode", null));
            body.put("country",  params.getOrDefault("country", null));

            // user registration pe INACTIVE
            body.put("status", "INACTIVE");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String,Object>> entity = new HttpEntity<>(body, headers);

            restTemplate.postForEntity(backendBase + "/users/add", entity, String.class);
            model.addAttribute("message", "Registered successfully...");
        } catch (HttpStatusCodeException e) {
            String body = e.getResponseBodyAsString();
            if (body != null && body.contains("EMAIL")) {
                model.addAttribute("message", "This email is already registered. Please use a different email.");
            } else {
                model.addAttribute("message", "Error from server: " + e.getStatusCode());
            }
        } catch (Exception e) {
            model.addAttribute("message", "Unexpected error: " + e.getMessage());
        }
        return "user-register";
    }

    // =============== LOGIN ===============
    @GetMapping("/login")
    public String showLogin() {
        return "user-login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String userName,
                          @RequestParam String password,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String json = String.format("{\"userName\":\"%s\",\"password\":\"%s\"}", userName, password);
            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            ResponseEntity<String> resp =
                    restTemplate.postForEntity(backendBase + "/users/login", entity, String.class);

            if (resp.getStatusCode().is2xxSuccessful()) {
                // UserId abhi null hai, backend se mile to set kar sakte ho
                Integer userId = null;
                SessionUtil.setUser(session, "USER", userId, userName);

                redirectAttributes.addFlashAttribute("message", "Login successful");
                return "redirect:/users-ui/dashboard";
            } else if (resp.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                redirectAttributes.addFlashAttribute("error", "Invalid username or password");
                return "redirect:/users-ui/login";
            } else if (resp.getStatusCode() == HttpStatus.FORBIDDEN) {
                redirectAttributes.addFlashAttribute("error", "Your account is inactive. Please contact admin.");
                return "redirect:/users-ui/login";
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Login failed: " + resp.getStatusCodeValue());
                return "redirect:/users-ui/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/users-ui/login";
        }
    }

    // =============== DASHBOARD ===============
    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session,
                                @ModelAttribute("message") String message,
                                @ModelAttribute("error") String error) {

        String userName = SessionUtil.getUserName(session);
        if (userName == null) {
            return "redirect:/users-ui/login";
        }

        // JSP me welcome text ke liye
        model.addAttribute("userName", userName);

        // ---- All Apps ----
        try {
            ResponseEntity<List> appsResp =
                    restTemplate.exchange(backendBase + "/apps",
                            HttpMethod.GET, null, List.class);
            model.addAttribute("apps", appsResp.getBody());
        } catch (Exception e) {
            model.addAttribute("apps", List.of());
            model.addAttribute("error", "Cannot load apps: " + e.getMessage());
        }

        // ---- My Downloads ----  *** IMPORTANT FIX ***
        try {
            // yahan pe /downloads/{userName} hit kar rahe hain
            ResponseEntity<List> dlResp =
                    restTemplate.exchange(backendBase + "/downloads/" + userName,
                            HttpMethod.GET, null, List.class);
            model.addAttribute("downloads", dlResp.getBody());
        } catch (Exception e) {
            model.addAttribute("downloads", List.of());
        }

        if (message != null && !message.isBlank()) {
            model.addAttribute("message", message);
        }
        if (error != null && !error.isBlank()) {
            model.addAttribute("error", error);
        }

        return "user-dashboard";
    }

    // =============== DOWNLOAD BUTTON ===============
    @PostMapping("/apps/{appId}/download")
    public String downloadApp(@PathVariable Long appId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        String userName = SessionUtil.getUserName(session);
        if (userName == null) {
            redirectAttributes.addFlashAttribute("error", "Please login again.");
            return "redirect:/users-ui/login";
        }

        String url = backendBase + "/downloads";

        Map<String, Object> body = new HashMap<>();
        body.put("userName", userName);
        body.put("appId", appId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> resp =
                    restTemplate.postForEntity(url, entity, String.class);

            if (resp.getStatusCode().is2xxSuccessful()
                    || resp.getStatusCode() == HttpStatus.CREATED) {
                redirectAttributes.addFlashAttribute("message", "App downloaded successfully");
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Failed to download app: " + resp.getStatusCodeValue());
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                redirectAttributes.addFlashAttribute("error", "App already downloaded by this user");
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Failed to download app: " + e.getStatusCode() +
                                " : \"" + e.getResponseBodyAsString() + "\"");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to download app: " + e.getMessage());
        }

        // Important: naya data lane ke liye redirect to /dashboard
        return "redirect:/users-ui/dashboard";
    }

    // (baaki /all, /{id} methods chaho to as-is rakh sakte ho)
}
