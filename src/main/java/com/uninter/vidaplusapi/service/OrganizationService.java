package com.uninter.vidaplusapi.service;

import com.uninter.vidaplusapi.dto.*;
import com.uninter.vidaplusapi.dto.exception.OrganizationNotFoundException;
import com.uninter.vidaplusapi.model.Organization;
import com.uninter.vidaplusapi.repository.OrganizationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OrganizationService  {

    private final OrganizationRepository repository;

    public Optional<OrganizationResponseDTO> save(OrganizationRequestDTO dto) {
        final var organization = dto.toOrganization();
        return Optional.of(repository.save(organization).toDTO());
    }

    public Page<OrganizationResponseDTO> findAll(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Organization> users = repository.findAll(pageable);
        return users.map(Organization::toDTO);
    }

    public OrganizationResponseDTO findById(UUID organizationId) {
        return repository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(organizationId)).toDTO();
    }

    public void deleteById(UUID organizationId) {
        final var organization = findById(organizationId);
        repository.deleteById(organization.getId());
    }

    @Transactional
    public OrganizationResponseDTO updatePartial(UUID organizationId, OrganizationUpdateRequestDTO dto) {
        final var organization = findById(organizationId);

        updateIfNotNull(dto.getName(), organization::setName);
        updateIfNotNull(dto.getCnpj(), organization::setCnpj);

        return organization;
    }

    private <T> void updateIfNotNull(T newValue, Consumer<T> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

}
