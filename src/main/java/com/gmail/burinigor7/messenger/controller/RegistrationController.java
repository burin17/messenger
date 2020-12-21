package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.dto.RegistrationDTO;
import com.gmail.burinigor7.messenger.repository.UserRepository;
import com.gmail.burinigor7.messenger.util.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationValidator registrationValidator;

    @Autowired
    public RegistrationController(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  RegistrationValidator registrationValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.registrationValidator = registrationValidator;
    }

    @GetMapping
    public String registrationPage(@ModelAttribute("form") RegistrationDTO registrationDTO) {
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid @ModelAttribute("form") RegistrationDTO registrationDTO,
                                      BindingResult bindingResult) {
        registrationValidator.validate(registrationDTO, bindingResult);
        if(bindingResult.hasErrors()) {
            return "/registration";
        }
        userRepository.save(registrationDTO.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
