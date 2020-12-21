package com.gmail.burinigor7.messenger.service;

import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.dto.EditProfileDTO;
import com.gmail.burinigor7.messenger.exception.UserNotFoundException;
import com.gmail.burinigor7.messenger.repository.UserRepository;
import com.gmail.burinigor7.messenger.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.List;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    @Autowired
    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User selfProfile(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public List<String> getUsersByUsernamePiece(String piece) {
        return userRepository.findAllByPieceOfUsername(piece);
    }

    public String editUser(Authentication authentication, EditProfileDTO editProfileDTO,
                           BindingResult bindingResult) {
        User user = selfProfile(authentication);
        if(!editProfileDTO.getUsername().equals(user.getUsername()) &&
        userRepository.findByUsername(editProfileDTO.getUsername()).isPresent())
            bindingResult.rejectValue("username", "", "Username already in use");
        if(bindingResult.hasErrors())
            return "/profile_edit";
        user.setUsername(editProfileDTO.getUsername());
        user.setFirstName(editProfileDTO.getFirstName());
        user.setLastName(editProfileDTO.getLastName());
        userRepository.save(user);
        ((SecurityUser)authentication.getPrincipal()).setUsername(user.getUsername());
        return "redirect:/profile/self";
    }
}
