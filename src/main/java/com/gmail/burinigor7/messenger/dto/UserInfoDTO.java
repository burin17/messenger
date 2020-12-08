package com.gmail.burinigor7.messenger.dto;

import com.gmail.burinigor7.messenger.domain.Role;
import com.gmail.burinigor7.messenger.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDTO {
    private String username;
    private String firstName;
    private String lastName;
    private Status status;
    private Role role;
}
