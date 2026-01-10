package com.uninter.vidaplusapi.service;

import com.uninter.vidaplusapi.dto.UserRequestDTO;
import com.uninter.vidaplusapi.dto.UserResponseDTO;
import com.uninter.vidaplusapi.dto.UserUpdateRequestDTO;
import com.uninter.vidaplusapi.dto.exception.UserNotFoundException;
import com.uninter.vidaplusapi.model.User;
import com.uninter.vidaplusapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserResponseDTO> save(UserRequestDTO dto, UUID organizationId) {
        final var user = dto.toUser();
        user.setOrganizationId(organizationId);
        user.setIsActive(Boolean.TRUE);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        return Optional.of(repository.save(user).toResponseDTO());

    }

    public Page<UserResponseDTO> findAll(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> users = repository.findAll(pageable);
        return users.map(User::toResponseDTO);
    }

    public UserResponseDTO findById(UUID userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)).toResponseDTO();
    }

    public void deleteById(UUID userId) {
        final var user = findById(userId);
        repository.deleteById(user.getId());
    }

    @Transactional
    public UserResponseDTO updatePartial(UUID userId, UserUpdateRequestDTO dto) {
        final var user = findById(userId);

        updateIfNotNull(dto.getUsername(), user::setUsername);
        updateIfNotNull(dto.getEmail(), user::setEmail);
        updateIfNotNull(dto.getFullName(), user::setFullName);

        return user;
    }

    private <T> void updateIfNotNull(T newValue, Consumer<T> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }
}