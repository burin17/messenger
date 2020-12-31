package com.gmail.burinigor7.messenger.rest;

import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.service.ComplaintService;
import com.gmail.burinigor7.messenger.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileRestController {
    private final ProfileService profileService;
    private final ComplaintService complaintService;

    @Autowired
    public ProfileRestController(ProfileService profileService,
                                 ComplaintService complaintService) {
        this.profileService = profileService;
        this.complaintService = complaintService;
    }

    @GetMapping("/piece")
    public List<User> findByUsernamePiece(@RequestParam String piece) {
        return profileService.getUsersByUsernamePiece(piece);
    }

    @PostMapping("/complaint")
    public boolean complaint(@Param("id") User target) {
        User authenticated = profileService.selfProfile();
        return complaintService.complaint(authenticated, target);
    }
}
