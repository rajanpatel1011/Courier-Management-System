package com.courier.management.repository;

import com.courier.management.entity.CourierOfficer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierOfficerRepository extends JpaRepository<CourierOfficer, Long> {

    // Find officer by name
    Optional<CourierOfficer> findByOfficerName(String officerName);

    // Find officers by office
    List<CourierOfficer> findByOffice(String office);

    // Find officer by name and password (for authentication)
    Optional<CourierOfficer> findByOfficerNameAndPassword(String officerName, String password);

    // Find officer by name, password and office (for authentication)
    Optional<CourierOfficer> findByOfficerNameAndPasswordAndOffice(String officerName, String password, String office);

    // Find officers by email
    Optional<CourierOfficer> findByEmail(String email);

    // Check if officer name exists
    boolean existsByOfficerName(String officerName);

    // Check if email exists
    boolean existsByEmail(String email);
}