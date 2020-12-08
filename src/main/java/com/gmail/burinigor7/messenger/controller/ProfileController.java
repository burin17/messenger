package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/self")
    public String profilePage(Principal principal, Model model) {
        model.addAttribute("profile", profileService.selfProfile(principal));
        return "profile";
    }
}
