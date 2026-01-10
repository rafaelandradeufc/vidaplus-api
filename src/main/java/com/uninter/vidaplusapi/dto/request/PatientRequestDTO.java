package com.uninter.vidaplusapi.dto.request;

import com.uninter.vidaplusapi.model.Patient;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDTO {

    @NotBlank(message = "Número do prontuário é obrigatório")
    private String medicalRecordNumber;

    @NotBlank(message = "Nome completo é obrigatório")
    private String fullName;

    @NotBlank(message = "Data de nascimento é obrigatória")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gênero é obrigatório")
    private String gender;

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "Endereço é obrigatório")
    private String address;

    public Patient toPatient() {
        return Patient.builder()
                .medicalRecordNumber(this.medicalRecordNumber)
                .fullName(this.fullName)
                .dateOfBirth(this.dateOfBirth)
                .gender(this.gender)
                .cpf(this.cpf)
                .address(this.address)
                .build();
    }
}
