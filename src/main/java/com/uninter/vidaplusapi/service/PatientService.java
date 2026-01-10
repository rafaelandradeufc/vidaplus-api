package com.uninter.vidaplusapi.service;

import com.uninter.vidaplusapi.dto.exception.PatientNotFoundException;
import com.uninter.vidaplusapi.dto.request.PatientRequestDTO;
import com.uninter.vidaplusapi.dto.request.PatientUpdateRequestDTO;
import com.uninter.vidaplusapi.dto.response.PatientResponseDTO;
import com.uninter.vidaplusapi.model.Patient;
import com.uninter.vidaplusapi.repository.PatientRepository;
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
public class PatientService {

    private final PatientRepository repository;

    public Optional<PatientResponseDTO> save(PatientRequestDTO dto, UUID organizationId) {
        final var patient = dto.toPatient();
        patient.setOrganizationId(organizationId);
        return Optional.of(repository.save(patient).toResponseDTO());
    }

    public Page<PatientResponseDTO> findAll(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Patient> patients = repository.findAll(pageable);
        return patients.map(Patient::toResponseDTO);
    }

    public PatientResponseDTO findById(UUID patientId) {
        return repository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException(patientId)).toResponseDTO();
    }

    public void deleteById(UUID patientId) {
        final var patient = findById(patientId);
        repository.deleteById(patient.getId());
    }

    @Transactional
    public PatientResponseDTO updatePartial(UUID patientId, PatientUpdateRequestDTO dto) {
        final var patient = findById(patientId);

        updateIfNotNull(dto.getMedicalRecordNumber(), patient::setMedicalRecordNumber);
        updateIfNotNull(dto.getFullName(), patient::setFullName);
        updateIfNotNull(dto.getDateOfBirth(), patient::setDateOfBirth);
        updateIfNotNull(dto.getGender(), patient::setGender);
        updateIfNotNull(dto.getCpf(), patient::setCpf);
        updateIfNotNull(dto.getAddress(), patient::setAddress);

        return patient;
    }

    private <T> void updateIfNotNull(T newValue, Consumer<T> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

}
