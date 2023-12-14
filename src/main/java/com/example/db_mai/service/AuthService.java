package com.example.db_mai.service;

import com.example.db_mai.model.Customer;
import com.example.db_mai.repository.CustomerRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(@NotNull String username, @NotNull String password) {
        var user = new Customer();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        customerRepository.save(user);
    }
}
