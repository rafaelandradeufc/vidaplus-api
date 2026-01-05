package com.uninter.vidaplusapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "professionals")
@Data
@NoArgsConstructor
public class Professional extends BaseEntity {

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column
    private String crm;

    @Column(name = "specialties")
    private String specialties;

    @Column(columnDefinition = "jsonb")
    private String availability;

    @Column(columnDefinition = "jsonb")
    private String contact;
}
