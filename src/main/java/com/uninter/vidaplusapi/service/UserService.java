package com.uninter.vidaplusapi.service;

import com.uninter.vidaplusapi.dto.UserRequestDTO;
import com.uninter.vidaplusapi.dto.UserResponseDTO;
import com.uninter.vidaplusapi.model.User;
import com.uninter.vidaplusapi.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> findByUsername(UUID orgId, String username) {
        return repository.findByUsernameAndOrganizationId(username, orgId);
    }

    public Optional<User> save(UserRequestDTO userResponseDTO) {
        return Optional.of(repository.save(userResponseDTO.toUser()));
    }

    public Page<UserResponseDTO> findAll(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> users = repository.findAll(pageable);
        return users.map(User::toDTO);
    }

    public void changePassword(UUID userId, UUID orgId, String newHash) {
        repository.updatePassword(userId, orgId, newHash);
    }

    public void delete(UUID orgId) {
        repository.deleteById(orgId);
    }
}