package com.uninter.vidaplusapi.model;


import com.uninter.vidaplusapi.crypto.EncryptedStringConverter;
import com.uninter.vidaplusapi.dto.UserResponseDTO;
import com.uninter.vidaplusapi.model.type.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "phone_encrypted")
    private String phone;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "organization_id", columnDefinition = "uuid", nullable = false)
    private UUID organizationId;

    public UserResponseDTO toResponseDTO() {
       return UserResponseDTO.builder()
                .id(this.getId())
                .username(this.getUsername())
                .email(this.getEmail())
                .fullName(this.getFullName())
                .createdAt(this.getCreatedAt())
                .build();

    }

    @PrePersist
    public void prePersist() {
        if (roleType == null) {
            roleType = RoleType.STANDARD;
        }
    }

}
