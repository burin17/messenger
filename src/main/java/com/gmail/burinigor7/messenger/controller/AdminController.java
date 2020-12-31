package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.domain.Complaint;
import com.gmail.burinigor7.messenger.domain.Message;
import com.gmail.burinigor7.messenger.domain.Role;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.dto.EditProfileDTO;
import com.gmail.burinigor7.messenger.repository.ComplaintRepository;
import com.gmail.burinigor7.messenger.service.MessageService;
import com.gmail.burinigor7.messenger.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final ProfileService profileService;
    private final MessageService messageService;


    @Autowired
    public AdminController(ProfileService profileService,
                           MessageService messageService) {
        this.profileService = profileService;
        this.messageService = messageService;
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

    @GetMapping("/dialog/{id1}/{id2}")
    public String dialog(@PathVariable("id1") User user1,
                         @PathVariable("id2") User user2,
                         Model model) {
        List<Message> dialog = messageService.getDialog(user1,
                                                        user2);
        model.addAttribute("dialog", dialog);
        model.addAttribute("user1", user1);
        model.addAttribute("user2", user2);
        return "admin_dialog";
    }

    @GetMapping("/complaints")
    public String complaints(Model model) {
        List<Complaint> complaints = profileService.allComplaints();
        model.addAttribute("complaints", complaints);
        return "complaints";
    }
}
