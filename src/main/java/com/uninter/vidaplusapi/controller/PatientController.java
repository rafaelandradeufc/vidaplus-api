package com.uninter.vidaplusapi.controller;

import com.uninter.vidaplusapi.dto.request.PatientRequestDTO;
import com.uninter.vidaplusapi.dto.request.PatientUpdateRequestDTO;
import com.uninter.vidaplusapi.dto.response.PatientResponseDTO;
import com.uninter.vidaplusapi.service.PatientService;
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
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "Operações sobre pacientes")
public class PatientController {

    private final PatientService service;

    @Operation(
            summary = "Busca paginada de pacientes",
            description = "Retorna pacientes com paginação e ordenação")
    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> list(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "fullName,asc") String sortParam) {

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
            summary = "Adiciona um novo paciente",
            description = "Cria um novo paciente no sistema")
    @PostMapping
    public ResponseEntity<PatientResponseDTO> addPatient(
            @Valid @RequestBody final PatientRequestDTO dto,
            @RequestHeader UUID organizationId) {
        return ResponseEntity.ok(service.save(dto, organizationId).orElseThrow());
    }

    @Operation(
            summary = "Remove um paciente",
            description = "Remove um paciente existente pelo ID")
    @DeleteMapping("/{patientId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente encontrado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletePatient(@PathVariable UUID patientId) {
        service.deleteById(patientId);
        return ResponseEntity.noContent().build();

    }

    @Operation(
            summary = "Busca um paciente pelo ID",
            description = "Retorna os detalhes de um paciente específico pelo ID")
    @GetMapping("/{patientId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente encontrado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor")
    })
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable UUID patientId) {
        return ResponseEntity.ok(service.findById(patientId));
    }

    @Operation(
            summary = "Atualiza um paciente existente",
            description = "Atualiza os detalhes de um paciente existente pelo ID")
    @PutMapping("/{patientId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Paciente encontrado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor")
    })
    public ResponseEntity<PatientResponseDTO> updatePatientById(
            @PathVariable UUID patientId,
            @Valid @RequestBody PatientUpdateRequestDTO dto) {
        return ResponseEntity.ok(service.updatePartial(patientId, dto));
    }
}
