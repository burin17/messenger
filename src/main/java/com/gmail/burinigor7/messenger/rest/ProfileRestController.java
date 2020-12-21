package com.gmail.burinigor7.messenger.rest;

import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileRestController {
    private final ProfileService profileService;

    @Autowired
    public ProfileRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/piece")
    public List<User> findByUsernamePiece(@RequestParam String piece) {
        return profileService.getUsersByUsernamePiece(piece);
    }
}
