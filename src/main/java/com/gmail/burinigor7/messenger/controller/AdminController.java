package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.dto.EditProfileDTO;
import com.gmail.burinigor7.messenger.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final ProfileService profileService;

    @Autowired
    public AdminController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile/edit/{id}")
    public String editProfilePage(@PathVariable("id") User profile, Model model) {
        model.addAttribute("profile", profile);
        return "profile_edit";
    }

    @PostMapping("/profile/edit/{id}")
    public String editProfileAction(@Valid @ModelAttribute("profile") EditProfileDTO editProfileDTO,
                                    @PathVariable("id") User profile,
                                    BindingResult bindingResult,
                                    Model model) {
        return profileService.editUser(editProfileDTO, profile, bindingResult);
    }
}
