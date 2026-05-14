package com.courier.management.repository;

import com.courier.management.entity.CourierTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierTrackRepository extends JpaRepository<CourierTrack, Long> {

    // Find tracking records by courier ID
    List<CourierTrack> findByCourierIdOrderByBookingTimeDesc(Long courierId);

    // Find tracking records by consignment number
    List<CourierTrack> findByConsignmentNoOrderByBookingTimeDesc(String consignmentNo);

    // Find tracking records by status
    List<CourierTrack> findByStatus(String status);

    // Find tracking records by current city
    List<CourierTrack> findByCurrentCity(String currentCity);

    // Custom query to get latest tracking record for a courier
    @Query("SELECT ct FROM CourierTrack ct WHERE ct.courierId = :courierId ORDER BY ct.bookingTime DESC")
    List<CourierTrack> findLatestTrackingByCourierId(@Param("courierId") Long courierId);

    // Count tracking records by status
    long countByStatus(String status);

    // Find all tracking records ordered by booking time
    List<CourierTrack> findAllByOrderByBookingTimeDesc();
}