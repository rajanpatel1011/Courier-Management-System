package com.courier.management.service;

import com.courier.management.entity.CourierTrack;
import com.courier.management.repository.CourierTrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourierTrackService {

    private final CourierTrackRepository courierTrackRepository;

    // Create a new tracking record
    public CourierTrack createTrackingRecord(CourierTrack track) {
        // Set booking time if not set
        if (track.getBookingTime() == null) {
            track.setBookingTime(LocalDateTime.now());
        }

        return courierTrackRepository.save(track);
    }

    // Get tracking records by courier ID
    @Transactional(readOnly = true)
    public List<CourierTrack> getTrackingByCourierId(Long courierId) {
        return courierTrackRepository.findByCourierIdOrderByBookingTimeDesc(courierId);
    }

    // Get tracking records by consignment number
    @Transactional(readOnly = true)
    public List<CourierTrack> getTrackingByConsignmentNo(String consignmentNo) {
        return courierTrackRepository.findByConsignmentNoOrderByBookingTimeDesc(consignmentNo);
    }

    // Get all tracking records
    @Transactional(readOnly = true)
    public List<CourierTrack> getAllTrackingRecords() {
        return courierTrackRepository.findAllByOrderByBookingTimeDesc();
    }

    // Get tracking records by status
    @Transactional(readOnly = true)
    public List<CourierTrack> getTrackingByStatus(String status) {
        return courierTrackRepository.findByStatus(status);
    }

    // Get tracking records by current city
    @Transactional(readOnly = true)
    public List<CourierTrack> getTrackingByCurrentCity(String currentCity) {
        return courierTrackRepository.findByCurrentCity(currentCity);
    }

    // Update tracking record
    public CourierTrack updateTrackingRecord(CourierTrack track) {
        if (!courierTrackRepository.existsById(track.getId())) {
            throw new RuntimeException("Tracking record not found with id: " + track.getId());
        }
        return courierTrackRepository.save(track);
    }

    // Delete tracking record
    public void deleteTrackingRecord(Long id) {
        if (!courierTrackRepository.existsById(id)) {
            throw new RuntimeException("Tracking record not found with id: " + id);
        }
        courierTrackRepository.deleteById(id);
    }

    // Count tracking records by status
    @Transactional(readOnly = true)
    public long countTrackingByStatus(String status) {
        return courierTrackRepository.countByStatus(status);
    }

    // Add status update for courier
    public CourierTrack addStatusUpdate(Long courierId, String consignmentNo, String currentCity, String status, String comments) {
        CourierTrack track = new CourierTrack();
        track.setCourierId(courierId);
        track.setConsignmentNo(consignmentNo);
        track.setCurrentCity(currentCity);
        track.setStatus(status);
        track.setComments(comments);
        track.setBookingTime(LocalDateTime.now());

        return createTrackingRecord(track);
    }
}