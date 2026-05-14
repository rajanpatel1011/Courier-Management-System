package com.courier.management.service;

import com.courier.management.entity.CourierOfficer;
import com.courier.management.repository.CourierOfficerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CourierOfficerService {

    private final CourierOfficerRepository courierOfficerRepository;

    // Create a new courier officer
    public CourierOfficer createCourierOfficer(CourierOfficer officer) {
        // Set registration date if not set
        if (officer.getRegistrationDate() == null) {
            officer.setRegistrationDate(LocalDateTime.now());
        }

        // Check if officer name already exists
        if (courierOfficerRepository.existsByOfficerName(officer.getOfficerName())) {
            throw new RuntimeException("Officer name already exists: " + officer.getOfficerName());
        }

        // Check if email already exists
        if (courierOfficerRepository.existsByEmail(officer.getEmail())) {
            throw new RuntimeException("Email already exists: " + officer.getEmail());
        }

        return courierOfficerRepository.save(officer);
    }

    // Get officer by ID
    @Transactional(readOnly = true)
    public Optional<CourierOfficer> getCourierOfficerById(Long id) {
        return courierOfficerRepository.findById(id);
    }

    // Get officer by name
    @Transactional(readOnly = true)
    public Optional<CourierOfficer> getCourierOfficerByName(String officerName) {
        return courierOfficerRepository.findByOfficerName(officerName);
    }

    // Get all officers
    @Transactional(readOnly = true)
    public List<CourierOfficer> getAllCourierOfficers() {
        return courierOfficerRepository.findAll();
    }

    // Get officers by office
    @Transactional(readOnly = true)
    public List<CourierOfficer> getCourierOfficersByOffice(String office) {
        return courierOfficerRepository.findByOffice(office);
    }

    // Authenticate officer
    @Transactional(readOnly = true)
    public Optional<CourierOfficer> authenticateOfficer(String officerName, String password, String office) {
        return courierOfficerRepository.findByOfficerNameAndPasswordAndOffice(officerName, password, office);
    }

    // Update officer
    public CourierOfficer updateCourierOfficer(CourierOfficer officer) {
        if (!courierOfficerRepository.existsById(officer.getId())) {
            throw new RuntimeException("Courier officer not found with id: " + officer.getId());
        }

        // Check if email is being changed and if it already exists
        Optional<CourierOfficer> existingOfficer = courierOfficerRepository.findById(officer.getId());
        if (existingOfficer.isPresent() && !existingOfficer.get().getEmail().equals(officer.getEmail())) {
            if (courierOfficerRepository.existsByEmail(officer.getEmail())) {
                throw new RuntimeException("Email already exists: " + officer.getEmail());
            }
        }

        return courierOfficerRepository.save(officer);
    }

    // Delete officer
    public void deleteCourierOfficer(Long id) {
        if (!courierOfficerRepository.existsById(id)) {
            throw new RuntimeException("Courier officer not found with id: " + id);
        }
        courierOfficerRepository.deleteById(id);
    }

    // Check if officer name exists
    @Transactional(readOnly = true)
    public boolean officerNameExists(String officerName) {
        return courierOfficerRepository.existsByOfficerName(officerName);
    }

    // Check if email exists
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return courierOfficerRepository.existsByEmail(email);
    }
}