package com.courier.management.service;

import com.courier.management.entity.Office;
import com.courier.management.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficeService {

    private final OfficeRepository officeRepository;

    // Create a new office
    public Office createOffice(Office office) {
        // Check if office name already exists
        if (officeRepository.existsByOfficeName(office.getOfficeName())) {
            throw new RuntimeException("Office name already exists: " + office.getOfficeName());
        }

        return officeRepository.save(office);
    }

    // Get office by ID
    @Transactional(readOnly = true)
    public Optional<Office> getOfficeById(Long id) {
        return officeRepository.findById(id);
    }

    // Get office by name
    @Transactional(readOnly = true)
    public Optional<Office> getOfficeByName(String officeName) {
        return officeRepository.findByOfficeName(officeName);
    }

    // Get all offices
    @Transactional(readOnly = true)
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    // Get offices by city
    @Transactional(readOnly = true)
    public List<Office> getOfficesByCity(String city) {
        return officeRepository.findByCity(city);
    }

    // Search offices by contact person
    @Transactional(readOnly = true)
    public List<Office> searchByContactPerson(String contactPerson) {
        return officeRepository.findByContactPersonContainingIgnoreCase(contactPerson);
    }

    // Update office
    public Office updateOffice(Office office) {
        if (!officeRepository.existsById(office.getId())) {
            throw new RuntimeException("Office not found with id: " + office.getId());
        }

        // Check if office name is being changed and if it already exists
        Optional<Office> existingOffice = officeRepository.findById(office.getId());
        if (existingOffice.isPresent() && !existingOffice.get().getOfficeName().equals(office.getOfficeName())) {
            if (officeRepository.existsByOfficeName(office.getOfficeName())) {
                throw new RuntimeException("Office name already exists: " + office.getOfficeName());
            }
        }

        return officeRepository.save(office);
    }

    // Delete office
    public void deleteOffice(Long id) {
        if (!officeRepository.existsById(id)) {
            throw new RuntimeException("Office not found with id: " + id);
        }
        officeRepository.deleteById(id);
    }

    // Check if office name exists
    @Transactional(readOnly = true)
    public boolean officeNameExists(String officeName) {
        return officeRepository.existsByOfficeName(officeName);
    }

    // Check if city has offices
    @Transactional(readOnly = true)
    public boolean cityHasOffices(String city) {
        return officeRepository.existsByCity(city);
    }
}