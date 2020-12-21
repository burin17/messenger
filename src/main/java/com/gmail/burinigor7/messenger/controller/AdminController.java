package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.domain.Role;
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
        if(profile.getRole() == Role.USER) {
            model.addAttribute("profile", profile);
            return "profile_edit";
        } return "redirect:/profile/" + profile.getId();
    }

    @PostMapping("/profile/edit/{id}")
    public String editProfileAction(@Valid @ModelAttribute("profile") EditProfileDTO editProfileDTO,
                                    @PathVariable("id") User user,
                                    BindingResult bindingResult) {
        if(user.getRole() == Role.USER) {
            editProfileDTO.setId(user.getId());
            return profileService.editUser(editProfileDTO, user, bindingResult);
        }
        return "redirect:/profile/" + user.getId();
    }
}
