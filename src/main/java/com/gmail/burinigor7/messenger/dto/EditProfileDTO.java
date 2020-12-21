package com.gmail.burinigor7.messenger.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Component
public class EditProfileDTO {
    @NotBlank(message = "Incorrect username")
    @Size(min = 5, message = "Length must be more than 5")
    private String username;

    @NotBlank(message = "Must not contain only spaces")
    private String firstName;

    @NotBlank(message = "Must not contain only spaces")
    private String lastName;
}
