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
@Table(name = "teleconsultation_recordings")
@Data
@NoArgsConstructor
public class TeleconsultationRecording extends BaseEntity {

    @Column(name = "session_id", columnDefinition = "uuid")
    private UUID sessionId;

    @Column(name = "s3_key")
    private String s3Key;

    @Column(name = "s3_bucket")
    private String s3Bucket;

    @Column(name = "encryption_metadata", columnDefinition = "jsonb")
    private String encryptionMetadata;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "retention_until")
    private Instant retentionUntil;
}