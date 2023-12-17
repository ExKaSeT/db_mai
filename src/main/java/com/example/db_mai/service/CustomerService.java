package com.example.db_mai.service;

import com.example.db_mai.exception.EntityNotFoundException;
import com.example.db_mai.model.Customer;
import com.example.db_mai.repository.CustomerRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer update(Long customerId, @Nullable String name) {
        var user = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        user.setName(name);
        return customerRepository.save(user);
    }

    public Customer get(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }
}
