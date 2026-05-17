package com.courier.management.repository;

import com.courier.management.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    // Find courier by consignment number
    Optional<Courier> findByConsignmentNo(String consignmentNo);

    Optional<Courier> findByConsignmentNoIgnoreCase(String consignmentNo);

    // Find couriers by status
    List<Courier> findByStatus(String status);

    // Find couriers by shipper name
    List<Courier> findByShipperNameContainingIgnoreCase(String shipperName);

    // Find couriers by receiver name
    List<Courier> findByReceiverNameContainingIgnoreCase(String receiverName);

    // Find delivered couriers
    List<Courier> findByStatusOrderByBookingDateDesc(String status);

    // Find pending couriers (not delivered)
    List<Courier> findByStatusNotOrderByBookingDateDesc(String status);

    // Find couriers by booking mode
    List<Courier> findByBookingMode(String bookingMode);

    // Custom query to find couriers by date range
    @Query("SELECT c FROM Courier c WHERE c.bookingDate BETWEEN :startDate AND :endDate ORDER BY c.bookingDate DESC")
    List<Courier> findByBookingDateBetween(@Param("startDate") java.time.LocalDate startDate, @Param("endDate") java.time.LocalDate endDate);

    // Count couriers by status
    long countByStatus(String status);

    // Check if consignment number exists
    boolean existsByConsignmentNo(String consignmentNo);
}