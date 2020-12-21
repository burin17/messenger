package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.dto.EditProfileDTO;
import com.gmail.burinigor7.messenger.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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

    @GetMapping("/search")
    public String searchProfilePage() {
        return "search_profile";
    }

    @GetMapping("/edit")
    public String editProfilePage(Principal principal, Model model) {
        model.addAttribute("profile", profileService.selfProfile(principal));
        return "profile_edit";
    }

    @PostMapping("/edit")
    public String editProfileAction(@Valid @ModelAttribute("profile") EditProfileDTO editProfileDTO,
                                    BindingResult bindingResult,
                                    Authentication authentication) {
        return profileService.editUser(authentication, editProfileDTO, bindingResult);
    }
}
