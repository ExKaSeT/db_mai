package com.example.db_mai.service;

import com.example.db_mai.model.Customer;
import com.example.db_mai.repository.CustomerRepository;
import com.example.db_mai.security.UserInfoDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Set;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Unable to find user with username: " + username));

        return new UserInfoDetails(customer, Set.of(new SimpleGrantedAuthority("USER")));
    }
}
