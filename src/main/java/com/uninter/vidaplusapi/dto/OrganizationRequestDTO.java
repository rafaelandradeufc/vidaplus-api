package com.uninter.vidaplusapi.dto;

import com.uninter.vidaplusapi.model.Organization;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequestDTO {

    @NotBlank(message = "name é obrigatório")
    private String name;

    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;

    public Organization toOrganization() {
        return Organization.builder()
                .name(this.name)
                .cnpj(this.cnpj)
                .build();
    }
}
