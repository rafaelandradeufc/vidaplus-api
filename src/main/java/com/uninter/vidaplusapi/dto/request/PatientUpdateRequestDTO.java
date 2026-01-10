package com.uninter.vidaplusapi.dto.request;


import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class PatientUpdateRequestDTO {

    @Nullable
    private String medicalRecordNumber;

    @Nullable
    private String fullName;

    @Nullable
    private LocalDate dateOfBirth;

    @Nullable
    private String gender;

    @Nullable
    private String cpf;

    @Nullable
    private String address;
}
