package com.example.frontend.controller;

import com.example.frontend.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String logout(HttpSession session){
        SessionUtil.clear(session);
        return "redirect:/";
    }
}
