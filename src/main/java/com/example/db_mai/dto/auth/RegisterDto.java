package com.example.db_mai.dto.auth;

import com.example.db_mai.validation.constraints.RegisterUsernameConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterDto {
    @NotNull(message = "Логин не может отсутствовать")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "В логине можно использовать только латинские символы")
    @RegisterUsernameConstraint
    private String username;
    @NotNull(message = "Пароль не может отсутствовать")
    private String password;
}
