package com.gmail.burinigor7.messenger.dto;

import com.gmail.burinigor7.messenger.domain.Role;
import com.gmail.burinigor7.messenger.domain.Status;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Component
public class EditProfileDTO {
    private Long id;
    @NotBlank(message = "Incorrect username")
    @Size(min = 5, message = "Length must be more than 5")
    private String username;

    @NotBlank(message = "Must not contain only spaces")
    private String firstName;

    @NotBlank(message = "Must not contain only spaces")
    private String lastName;

    private Role role;

    private Status status;
}
