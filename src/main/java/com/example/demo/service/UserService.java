package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserEntity> getAllUsers() {
        return repository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return repository.findById(id);
    }

    public UserEntity createUser(UserEntity user) {
        return repository.save(user);
    }

    public UserEntity updateUser(Long id, UserEntity updatedUser) {
        return repository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    return repository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
