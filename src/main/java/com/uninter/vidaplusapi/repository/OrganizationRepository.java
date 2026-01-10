package com.uninter.vidaplusapi.repository;

import com.uninter.vidaplusapi.model.Organization;
import com.uninter.vidaplusapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
}
