package com.uninter.vidaplusapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "teleconsultation_sessions")
@Data
@NoArgsConstructor
public class TeleconsultationSession extends BaseEntity {

    @Column(name = "encounter_id", columnDefinition = "uuid")
    private UUID encounterId;

    @Column(name = "patient_id", columnDefinition = "uuid")
    private UUID patientId;

    @Column(name = "professional_id", columnDefinition = "uuid")
    private UUID professionalId;

    @Column(name = "start_at")
    private Instant startAt;

    @Column(name = "end_at")
    private Instant endAt;

    @Column(name = "signaling_metadata", columnDefinition = "jsonb")
    private String signalingMetadata;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusType status;
}
