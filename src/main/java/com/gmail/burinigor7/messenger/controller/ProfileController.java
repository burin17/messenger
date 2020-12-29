package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.dto.EditProfileDTO;
import com.gmail.burinigor7.messenger.exception.UserNotFoundException;
import com.gmail.burinigor7.messenger.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String selfProfilePage(Model model) {
        System.out.println(RequestContextHolder.currentRequestAttributes().getSessionId());
        model.addAttribute("profile", profileService.selfProfile());
        model.addAttribute("isSelf", true);
        return "profile";
    }

    @GetMapping("/search")
    public String searchProfilePage() {
        return "search_profile";
    }

    @GetMapping("/edit")
    public String editProfilePage(Model model) {
        model.addAttribute("profile", profileService.selfProfile());
        return "profile_edit_self";
    }

    @PostMapping("/edit")
    public String editProfileAction(@Valid @ModelAttribute("profile") EditProfileDTO editProfileDTO,
                                    BindingResult bindingResult,
                                    Authentication authentication) {
        return profileService.editUser(authentication, editProfileDTO, bindingResult);
    }

    @GetMapping("/{id}")
    public String profilePage(@PathVariable("id") User profile,
                              Model model) {
        User authenticated = profileService.selfProfile();
        model.addAttribute("isSelf", authenticated.getUsername().equals(profile.getUsername()));
        model.addAttribute("profile", profile);
        return "profile";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String redirectToLogin(HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse,
                                  Authentication authentication) {
        new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
        return "/login";
    }
}
