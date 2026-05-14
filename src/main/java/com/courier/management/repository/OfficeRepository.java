package com.courier.management.repository;

import com.courier.management.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

    // Find office by name
    Optional<Office> findByOfficeName(String officeName);

    // Find offices by city
    List<Office> findByCity(String city);

    // Find offices by contact person
    List<Office> findByContactPersonContainingIgnoreCase(String contactPerson);

    // Check if office name exists
    boolean existsByOfficeName(String officeName);

    // Check if city has offices
    boolean existsByCity(String city);
}