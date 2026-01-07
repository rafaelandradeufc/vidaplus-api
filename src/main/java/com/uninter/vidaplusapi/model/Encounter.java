package com.uninter.vidaplusapi.model;

import com.uninter.vidaplusapi.model.type.EncounterType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "encounters")
@Data
@NoArgsConstructor
public class Encounter extends BaseEntity {

    @Column(name = "patient_id", columnDefinition = "uuid", nullable = false)
    private UUID patientId;

    @Column(name = "professional_id", columnDefinition = "uuid")
    private UUID professionalId;

    @Column(name = "appointment_id", columnDefinition = "uuid")
    private UUID appointmentId;

    @Column(name = "encounter_type")
    @Enumerated(EnumType.STRING)
    private EncounterType encounterType;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    @Column(columnDefinition = "jsonb")
    private String notes;

    @Column
    private String summary;

    @Column
    private Boolean confidential;

    @Column(name = "organization_id", columnDefinition = "uuid", nullable = false)
    private UUID organizationId;
}