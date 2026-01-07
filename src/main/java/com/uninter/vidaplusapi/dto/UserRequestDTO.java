package com.uninter.vidaplusapi.dto;

import com.uninter.vidaplusapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.uninter.vidaplusapi.crypto.HashUtils.generateHash;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;

    public User toUser() {
        return User.builder()
                .username(this.username)
                .passwordHash(generateHash(this.password))
                .email(this.email)
                .fullName(this.fullName)
                .phone(this.phone)
                .build();
    }
}
