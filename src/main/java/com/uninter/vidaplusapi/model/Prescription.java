package com.uninter.vidaplusapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
public class Prescription extends BaseEntity {

    @Column(name = "encounter_id", columnDefinition = "uuid")
    private UUID encounterId;

    @Column(name = "patient_id", columnDefinition = "uuid")
    private UUID patientId;

    @Column(name = "professional_id", columnDefinition = "uuid")
    private UUID professionalId;

    @Column(columnDefinition = "jsonb")
    private String medicationList;

    @Column(name = "signed_by", columnDefinition = "uuid")
    private UUID signedBy;

    @Column(name = "organization_id", columnDefinition = "uuid", nullable = false)
    private UUID organizationId;


}