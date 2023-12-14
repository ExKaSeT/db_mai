package com.example.db_mai.dto.auth;

import com.example.db_mai.validation.constraints.RegisterUsernameConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterDto {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Username should contain only Latin letters")
    @RegisterUsernameConstraint
    private String username;
    @NotNull
    private String password;
}
