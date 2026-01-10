package com.uninter.vidaplusapi.model;

import com.uninter.vidaplusapi.crypto.EncryptedStringConverter;
import com.uninter.vidaplusapi.dto.response.PatientResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "patients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends BaseEntity {

    @Column(name = "organization_id", columnDefinition = "uuid", nullable = false)
    private UUID organizationId;

    @Column(name = "medical_record_number")
    private String medicalRecordNumber;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column
    private String gender;

    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "cpf_encrypted")
    private String cpf;

    @Column(columnDefinition = "jsonb")
    private String address;

    public PatientResponseDTO toResponseDTO() {
        return PatientResponseDTO.builder()
                .id(this.getId())
                .medicalRecordNumber(this.getMedicalRecordNumber())
                .fullName(this.getFullName())
                .dateOfBirth(this.getDateOfBirth())
                .gender(this.getGender())
                .cpf(this.getCpf())
                .address(this.getAddress())
                .build();
    }

}
