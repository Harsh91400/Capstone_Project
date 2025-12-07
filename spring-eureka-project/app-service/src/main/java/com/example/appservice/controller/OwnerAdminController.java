package com.example.appservice.controller;

import com.example.appservice.service.AppOwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/owners")
public class OwnerAdminController {

    private final AppOwnerService appOwnerService;

    public OwnerAdminController(AppOwnerService appOwnerService) {
        this.appOwnerService = appOwnerService;
    }

    @GetMapping("/delete/{id}")
    public String deleteOwner(@PathVariable Long id,
                              RedirectAttributes redirectAttributes) {
        appOwnerService.deleteOwner(id);
        redirectAttributes.addFlashAttribute("success", "Owner deleted successfully.");
        return "redirect:/admins-ui/dashboard";
    }
}
