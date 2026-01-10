package com.uninter.vidaplusapi.model;


import com.uninter.vidaplusapi.crypto.EncryptedStringConverter;
import com.uninter.vidaplusapi.dto.UserResponseDTO;
import com.uninter.vidaplusapi.model.type.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

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

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "organization_id", columnDefinition = "uuid")
    private UUID organizationId;

    public UserResponseDTO toResponseDTO() {
       return UserResponseDTO.builder()
                .id(this.getId())
                .username(this.getUsername())
                .email(this.getEmail())
                .phone(this.getPhone())
                .fullName(this.getFullName())
                .createdAt(this.getCreatedAt())
                .build();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



}
