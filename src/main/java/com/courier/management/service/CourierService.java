package com.courier.management.service;

import com.courier.management.entity.Courier;
import com.courier.management.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CourierService {

    private final CourierRepository courierRepository;

    // Create a new courier
    public Courier createCourier(Courier courier) {
        // Generate consignment number if not provided
        if (courier.getConsignmentNo() == null || courier.getConsignmentNo().isEmpty()) {
            courier.setConsignmentNo(generateConsignmentNumber());
        }

        // Set booking date if not set
        if (courier.getBookingDate() == null) {
            courier.setBookingDate(LocalDate.now());
        }

        return courierRepository.save(courier);
    }

    // Get courier by ID
    @Transactional(readOnly = true)
    public Optional<Courier> getCourierById(Long id) {
        return courierRepository.findById(id);
    }

    // Get courier by consignment number
    @Transactional(readOnly = true)
    public Optional<Courier> getCourierByConsignmentNo(String consignmentNo) {
        return courierRepository.findByConsignmentNo(consignmentNo);
    }

    // Get all couriers
    @Transactional(readOnly = true)
    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    // Get couriers by status
    @Transactional(readOnly = true)
    public List<Courier> getCouriersByStatus(String status) {
        return courierRepository.findByStatus(status);
    }

    // Get delivered couriers
    @Transactional(readOnly = true)
    public List<Courier> getDeliveredCouriers() {
        return courierRepository.findByStatusOrderByBookingDateDesc("Delivered");
    }

    // Get pending couriers grouped by booking date
    @Transactional(readOnly = true)
    public List<Courier> getPendingCouriers() {
        return courierRepository.findByStatusNotOrderByBookingDateDesc("Delivered");
    }

    // Search couriers by shipper name
    @Transactional(readOnly = true)
    public List<Courier> searchByShipperName(String shipperName) {
        return courierRepository.findByShipperNameContainingIgnoreCase(shipperName);
    }

    // Search couriers by receiver name
    @Transactional(readOnly = true)
    public List<Courier> searchByReceiverName(String receiverName) {
        return courierRepository.findByReceiverNameContainingIgnoreCase(receiverName);
    }

    // Update courier status
    public Courier updateCourierStatus(Long id, String status) {
        Optional<Courier> courierOpt = courierRepository.findById(id);
        if (courierOpt.isPresent()) {
            Courier courier = courierOpt.get();
            courier.setStatus(status);
            return courierRepository.save(courier);
        }
        throw new RuntimeException("Courier not found with id: " + id);
    }

    // Mark courier as delivered
    public Courier markAsDelivered(Long id) {
        return updateCourierStatus(id, "Delivered");
    }

    // Update courier
    public Courier updateCourier(Courier courier) {
        if (!courierRepository.existsById(courier.getId())) {
            throw new RuntimeException("Courier not found with id: " + courier.getId());
        }
        return courierRepository.save(courier);
    }

    // Delete courier
    public void deleteCourier(Long id) {
        if (!courierRepository.existsById(id)) {
            throw new RuntimeException("Courier not found with id: " + id);
        }
        courierRepository.deleteById(id);
    }

    // Get couriers by date range
    @Transactional(readOnly = true)
    public List<Courier> getCouriersByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        return courierRepository.findByBookingDateBetween(startDate, endDate);
    }

    // Count couriers by status
    @Transactional(readOnly = true)
    public long countCouriersByStatus(String status) {
        if ("all".equalsIgnoreCase(status)) {
            return courierRepository.count();
        }
        return courierRepository.countByStatus(status);
    }

    // Count all couriers
    @Transactional(readOnly = true)
    public long countAllCouriers() {
        return courierRepository.count();
    }

    // Check if consignment number exists
    @Transactional(readOnly = true)
    public boolean consignmentNumberExists(String consignmentNo) {
        return courierRepository.existsByConsignmentNo(consignmentNo);
    }

    // Generate unique consignment number
    private String generateConsignmentNumber() {
        String consignmentNo;
        do {
            // Generate 8-character alphanumeric consignment number
            consignmentNo = generateRandomString(8).toUpperCase();
        } while (consignmentNumberExists(consignmentNo));

        return consignmentNo;
    }

    // Helper method to generate random string
    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}