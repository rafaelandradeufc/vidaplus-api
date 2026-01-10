package com.uninter.vidaplusapi.dto.request;

import com.uninter.vidaplusapi.model.User;
import com.uninter.vidaplusapi.model.type.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Nick é obrigatório")
    private String username;

    @NotBlank(message = "Senha é obrigatório")
    private String password;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Nome completo é obrigatório")
    private String fullName;

    @NotBlank(message = "Telefone é obrigatório")
    private String phone;

    public User toUser() {
        return User.builder()
                .username(this.username)
                .passwordHash(this.password)
                .email(this.email)
                .fullName(this.fullName)
                .phone(this.phone)
                .roleType(RoleType.STANDARD)
                .build();
    }
}
