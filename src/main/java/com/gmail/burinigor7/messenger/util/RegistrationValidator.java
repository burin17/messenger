package com.gmail.burinigor7.messenger.util;

import com.gmail.burinigor7.messenger.dto.RegistrationDTO;
import com.gmail.burinigor7.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {
    private final UserRepository userRepository;

    @Autowired
    public RegistrationValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationDTO form = (RegistrationDTO) target;
        if(!form.getPassword().equals(form.getPasswordConfirmation()))
            errors.rejectValue("passwordConfirmation", "", "Passwords don't match");
        if(userRepository.findByUsername(form.getUsername()).isPresent())
            errors.rejectValue("username", "", "Username already in use");
    }
}
