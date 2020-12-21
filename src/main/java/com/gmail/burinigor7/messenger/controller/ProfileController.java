package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.dto.EditProfileDTO;
import com.gmail.burinigor7.messenger.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String selfProfilePage(Principal principal, Model model) {
        model.addAttribute("profile", profileService.selfProfile(principal));
        model.addAttribute("isSelf", true);
        return "profile";
    }

    @GetMapping("/search")
    public String searchProfilePage() {
        return "search_profile";
    }

    @GetMapping("/edit")
    public String editProfilePage(Principal principal, Model model) {
        model.addAttribute("profile", profileService.selfProfile(principal));
        return "profile_edit_self";
    }

    @PostMapping("/edit")
    public String editProfileAction(@Valid @ModelAttribute("profile") EditProfileDTO editProfileDTO,
                                    BindingResult bindingResult,
                                    Authentication authentication,
                                    Model model) {
        return profileService.editUser(authentication, editProfileDTO, bindingResult);
    }

    @GetMapping("/{id}")
    public String profilePage(@PathVariable("id") User profile,
                              Principal principal,
                              Model model) {
        User authenticated = profileService.selfProfile(principal);
        model.addAttribute("isSelf", authenticated.getUsername().equals(profile.getUsername()));
        model.addAttribute("profile", profile);
        return "profile";
    }
}
