package com.uninter.vidaplusapi.repository;

import com.uninter.vidaplusapi.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsernameAndOrganizationId(String username, UUID organizationId);

    Optional<User> findByEmailAndOrganizationId(String email, UUID organizationId);

    Page<User> findByOrganizationId(UUID organizationId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.passwordHash = :passwordHash WHERE u.id = :id AND u.organizationId = :orgId")
    void updatePassword(@Param("id") UUID id,
                       @Param("orgId") UUID orgId,
                       @Param("passwordHash") String passwordHash);

}
