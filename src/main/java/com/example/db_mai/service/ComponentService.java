package com.example.db_mai.service;

import com.example.db_mai.exception.EntityNotFoundException;
import com.example.db_mai.model.Component;
import com.example.db_mai.model.ComponentOrder;
import com.example.db_mai.model.Customer;
import com.example.db_mai.repository.ComponentRepository;
import com.example.db_mai.repository.CustomerRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ComponentService {

    private final ComponentRepository componentRepository;

    public List<Component> search(@Nullable String term) {
       if (isNull(term)) {
           return componentRepository.findAll();
       }
       return componentRepository.findBySkuOrModel(term);
    }

    public Component get(Long id) {
        return componentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Component not found"));
    }

    public Component updateQuantity(Component component, Integer newQuantity) {
        if (newQuantity < 0) {
            throw new ValidationException("Кол-во не может быть меньше 0");
        }
        component.setQuantity(newQuantity);
        return componentRepository.save(component);
    }
}
