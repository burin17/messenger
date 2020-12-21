package com.gmail.burinigor7.messenger.service;

import com.gmail.burinigor7.messenger.domain.User;
import com.gmail.burinigor7.messenger.exception.UserNotFoundException;
import com.gmail.burinigor7.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
