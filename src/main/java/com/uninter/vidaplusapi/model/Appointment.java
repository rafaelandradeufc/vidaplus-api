package com.uninter.vidaplusapi.model;

import com.uninter.vidaplusapi.model.type.ChannelType;
import com.uninter.vidaplusapi.model.type.StatusType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
public class Appointment extends BaseEntity {

    @Column(name = "patient_id", columnDefinition = "uuid", nullable = false)
    private UUID patientId;

    @Column(name = "professional_id", columnDefinition = "uuid", nullable = false)
    private UUID professionalId;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Column
    private String reason;

    @Column
    @Enumerated(EnumType.STRING)
    private ChannelType channel;

    @Column(name = "organization_id", columnDefinition = "uuid", nullable = false)
    private UUID organizationId;

    @Column(name = "created_by", columnDefinition = "uuid")
    private UUID createdBy;
}
