package com.ganesh.crm.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordResetDTO {
    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}
