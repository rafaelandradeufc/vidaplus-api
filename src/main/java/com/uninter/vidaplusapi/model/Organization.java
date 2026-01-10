package com.uninter.vidaplusapi.model;

import com.uninter.vidaplusapi.crypto.EncryptedStringConverter;
import com.uninter.vidaplusapi.dto.OrganizationRequestDTO;
import com.uninter.vidaplusapi.dto.OrganizationResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "organizations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends BaseEntity {
    @Column(nullable = false)
    private String name;

    // CNPJ Encryptado norma LGPD
    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "cnpj_encrypted")
    private String cnpj;

    public OrganizationResponseDTO toDTO() {
        return OrganizationResponseDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .cnpj(this.getCnpj())
                .build();

    }

}