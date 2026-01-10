package com.uninter.vidaplusapi.dto;

import com.uninter.vidaplusapi.model.User;
import com.uninter.vidaplusapi.model.type.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Username é obrigatório")
    private String username;

    @NotBlank(message = "Password é obrigatório")
    private String password;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Fullname é obrigatório")
    private String fullName;

    @NotBlank(message = "Phone é obrigatório")
    private String phone;

    public User toUser() {
        return User.builder()
                .username(this.username)
                .passwordHash(generateHash(this.password))
                .email(this.email)
                .fullName(this.fullName)
                .phone(this.phone)
                .roleType(RoleType.STANDARD)
                .build();
    }
}
