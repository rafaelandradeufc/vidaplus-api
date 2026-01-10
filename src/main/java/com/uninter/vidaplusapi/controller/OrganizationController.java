package com.uninter.vidaplusapi.controller;

import com.uninter.vidaplusapi.dto.request.OrganizationRequestDTO;
import com.uninter.vidaplusapi.dto.request.OrganizationUpdateRequestDTO;
import com.uninter.vidaplusapi.dto.response.OrganizationResponseDTO;
import com.uninter.vidaplusapi.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
@Tag(name = "Organizações", description = "Operações sobre organizações")
public class OrganizationController {

    private final OrganizationService service;

    @Operation(
            summary = "Busca paginada de organizações",
            description = "Retorna organizações com paginação e ordenação")
    @GetMapping
    public ResponseEntity<Page<OrganizationResponseDTO>> list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name,asc") String sortParam) {

        String[] parts = sortParam.split(",");
        Sort sort = Sort.by(parts[0]);
        if (parts.length > 1 && "desc".equalsIgnoreCase(parts[1])) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        return ResponseEntity.ok(service.findAll( page, size, sort));
    }

    @Operation(
            summary = "Adiciona uma nova organização",
            description = "Cria uma nova organização no sistema")
    @PostMapping
    public ResponseEntity<OrganizationResponseDTO> addOrganization(
            @Valid @RequestBody final OrganizationRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto).orElseThrow());
    }

    @Operation(
            summary = "Remove uma organização",
            description = "Remove uma organização existente pelo ID")
    @DeleteMapping("/{organizationId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Organização encontrada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Organização não encontrada"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deleteOrganization(@PathVariable UUID organizationId) {
        service.deleteById(organizationId);
        return ResponseEntity.noContent().build();

    }

    @Operation(
            summary = "Busca uma organização pelo ID",
            description = "Retorna os detalhes de uma organização específica pelo ID")
    @GetMapping("/{organizationId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Organização encontrada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Organização não encontrada"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor")
    })
    public ResponseEntity<OrganizationResponseDTO> getOrganizationById(@PathVariable UUID organizationId) {
        return ResponseEntity.ok(service.findById(organizationId));
    }


    @Operation(
            summary = "Atualiza uma organização existente",
            description = "Atualiza os detalhes de uma organização existente pelo ID")
    @PutMapping("/{organizationId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Organização encontrada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Organização não encontrada"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor")
    })
    public ResponseEntity<OrganizationResponseDTO> updateOrganizationById(
            @PathVariable UUID organizationId,
            @Valid @RequestBody OrganizationUpdateRequestDTO dto) {
        return ResponseEntity.ok(service.updatePartial(organizationId, dto));
    }


}
