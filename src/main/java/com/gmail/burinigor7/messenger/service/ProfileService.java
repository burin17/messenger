package com.gmail.burinigor7.messenger.service;

import com.gmail.burinigor7.messenger.domain.Role;
import com.gmail.burinigor7.messenger.domain.Status;
import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.dto.EditProfileDTO;
import com.gmail.burinigor7.messenger.exception.AuthException;
import com.gmail.burinigor7.messenger.exception.UserNotFoundException;
import com.gmail.burinigor7.messenger.repository.UserRepository;
import com.gmail.burinigor7.messenger.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final SessionRegistryImpl sessionRegistry;

    @Autowired
    public ProfileService(UserRepository userRepository,
                          SessionRegistryImpl sessionRegistry){
        this.userRepository = userRepository;
        this.sessionRegistry = sessionRegistry;
    }

    public User selfProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(AuthException::new);
    }

    public User user(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getUsersByUsernamePiece(String piece) {
        List<User> users = userRepository.findAllByPieceOfUsername(piece);
                users.forEach(user -> {
                    user.setPassword(null);
                    user.setRole(null);
                    user.setLastName(null);
                    user.setFirstName(null);
                    user.setStatus(null);
                });
        return users;
    }

    public String editUser(Authentication authentication, EditProfileDTO editProfileDTO,
                           BindingResult bindingResult) {
        User user = selfProfile();
        if(!updateUser(user, editProfileDTO, bindingResult))
            return "/profile_edit_self";
        ((SecurityUser)authentication.getPrincipal()).setUsername(user.getUsername());
        return "redirect:/profile/self";
    }

    public String editUser(EditProfileDTO editProfileDTO,
                           User profile,
                           BindingResult bindingResult) {
        if(!updateUser(profile, editProfileDTO, bindingResult))
            return "/profile_edit";
        return "redirect:/profile/" + profile.getId();
    }

    private boolean updateUser(User profile, EditProfileDTO editProfileDTO,
                               BindingResult bindingResult) {
        if(!editProfileDTO.getUsername().equals(profile.getUsername()) &&
                userRepository.findByUsername(editProfileDTO.getUsername()).isPresent())
            bindingResult.rejectValue("username", "", "Username already in use");
        if(editProfileDTO.getStatus() != Status.ACTIVE &&
                editProfileDTO.getRole() == Role.ADMIN)
            bindingResult.rejectValue("status", "", "Incorrect status for ADMIN role");
        if(bindingResult.hasErrors())
            return false;
        profile.setUsername(editProfileDTO.getUsername());
        profile.setFirstName(editProfileDTO.getFirstName());
        profile.setLastName(editProfileDTO.getLastName());
        if(editProfileDTO.getRole() != null && editProfileDTO.getStatus() != null) {
            profile.setRole(editProfileDTO.getRole());
            profile.setStatus(editProfileDTO.getStatus());
        }
        userRepository.save(profile);
        if(profile.getStatus() != Status.ACTIVE)
            sessionRegistry.getAllSessions(new SecurityUser(profile.getUsername()), false)
                    .forEach(SessionInformation::expireNow);
        return true;
    }
}
