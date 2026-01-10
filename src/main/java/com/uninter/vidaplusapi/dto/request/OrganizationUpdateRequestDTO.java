package com.uninter.vidaplusapi.dto.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;




@Data
@Builder
@AllArgsConstructor
public class OrganizationUpdateRequestDTO {

    @Nullable
    private String name;

    @Nullable
    private String cnpj;

}
