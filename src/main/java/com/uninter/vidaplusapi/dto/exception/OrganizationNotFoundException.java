package com.uninter.vidaplusapi.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException(UUID organizationId) {
        super("Organização não encontrado com ID: " + organizationId);
    }
}
