package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.dto.UserInfoDTO;
import com.gmail.burinigor7.messenger.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @GetMapping("/search")
    public String searchProfilePage() {
        return "search_profile";
    }
}
