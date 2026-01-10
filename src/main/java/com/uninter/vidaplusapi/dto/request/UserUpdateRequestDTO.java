package com.uninter.vidaplusapi.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class UserUpdateRequestDTO {
    @Nullable
    private String username;

    @Nullable
    @Email
    private String email;

    @Nullable
    private String fullName;

}