package com.uninter.vidaplusapi.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.*;

@Value
@Data
@Builder
@AllArgsConstructor
public class UserUpdateRequestDTO {
    @Nullable
    String username;

    @Nullable
    @Email
    String email;

    @Nullable
    String fullName;


    public static UserUpdateRequestDTO toDTO(UserRequestDTO dto) {
        return UserUpdateRequestDTO.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .build();
    }
}