package com.gmail.burinigor7.messenger.controller;

import com.gmail.burinigor7.messenger.dto.RegistrationDTO;
import com.gmail.burinigor7.messenger.repository.UserRepository;
import com.gmail.burinigor7.messenger.util.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
                                      BindingResult bindingResult) throws InterruptedException {
        registrationValidator.validate(registrationDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/registration";
        }
        try {
            userRepository.save(registrationDTO.toUser(passwordEncoder));
        } catch (DataIntegrityViolationException e) {
            bindingResult.rejectValue("username", "", "Username already in use");
            return "/registration";
        }
        return "redirect:/login";
    }
}
