package com.uninter.vidaplusapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
    private UUID id;
    private String medicalRecordNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String cpf;
    private String address;
}
