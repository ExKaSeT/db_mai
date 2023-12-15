package com.example.db_mai.validation.validators;

import com.example.db_mai.repository.CustomerRepository;
import com.example.db_mai.validation.constraints.RegisterUsernameConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterUsernameValidator implements ConstraintValidator<RegisterUsernameConstraint, String> {

    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

        if (customerRepository.findByUsername(username).isPresent()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("User with username \"" + username + "\" already exists").addConstraintViolation();
            return false;
        }

        return true;
    }
}