package com.gmail.burinigor7.messenger.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPanelPage() {
        return "admin";
    }
}
