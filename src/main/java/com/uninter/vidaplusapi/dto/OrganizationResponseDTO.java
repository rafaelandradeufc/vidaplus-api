package com.uninter.vidaplusapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponseDTO {

    @NotBlank(message = "Id é obrigatório")
    private UUID id;

    @NotBlank(message = "Name é obrigatório")
    private String name;

    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;

}
