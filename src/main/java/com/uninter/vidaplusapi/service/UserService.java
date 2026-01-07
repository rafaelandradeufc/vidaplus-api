package com.uninter.vidaplusapi.service;

import com.uninter.vidaplusapi.dto.UserRequestDTO;
import com.uninter.vidaplusapi.dto.UserResponseDTO;
import com.uninter.vidaplusapi.dto.UserUpdateRequestDTO;
import com.uninter.vidaplusapi.dto.exception.UserNotFoundException;
import com.uninter.vidaplusapi.model.User;
import com.uninter.vidaplusapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class UserService {

    private final UserRepository repository;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> save(UserRequestDTO userResponseDTO, UUID organizationId) {
        final var user = userResponseDTO.toUser();
        user.setOrganizationId(organizationId);
        return Optional.of(repository.save(user));
    }

    public Page<UserResponseDTO> findAll(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> users = repository.findAll(pageable);
        return users.map(User::toResponseDTO);
    }

    public User findById(UUID userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public void deleteById(UUID userId) {
        final var user = findById(userId);
        repository.deleteById(user.getId());
    }

    @Transactional
    public User updatePartial(UUID userId, UserUpdateRequestDTO request) {
        User user = findById(userId);

        updateIfNotNull(request.getUsername(), user::setUsername);
        updateIfNotNull(request.getEmail(), user::setEmail);
        updateIfNotNull(request.getFullName(), user::setFullName);

        return user;
    }

    private <T> void updateIfNotNull(T newValue, Consumer<T> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }
}