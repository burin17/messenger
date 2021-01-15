package com.gmail.burinigor7.messenger.dto;

import com.gmail.burinigor7.messenger.domain.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Component
public class RegistrationDTO {

    @NotBlank(message = "Incorrect username")
    @Size(min = 5, message = "Length must be more than 4")
    private String username;

    @NotBlank(message = "Must not contain only spaces")
    private String firstName;

    @NotBlank(message = "Must not contain only spaces")
    private String lastName;

    @Size(min = 5, message = "Length must be more than 4")
    private String password;

    private String passwordConfirmation;

    public User toUser(PasswordEncoder encoder) {
        return new User(username, firstName, lastName, encoder.encode(password));
    }
}
