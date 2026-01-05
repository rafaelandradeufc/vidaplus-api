package com.uninter.vidaplusapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "organizations")
@Data
@NoArgsConstructor
public class Organization extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column
    private String timezone;

    @Column(columnDefinition = "jsonb")
    private String metadata;
}