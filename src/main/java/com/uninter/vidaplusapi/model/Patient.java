package com.uninter.vidaplusapi.model;

import com.uninter.vidaplusapi.crypto.EncryptedStringConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
public class Patient extends BaseEntity {

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

    @Column(name = "organization_id", columnDefinition = "uuid", nullable = false)
    private UUID organizationId;

}
