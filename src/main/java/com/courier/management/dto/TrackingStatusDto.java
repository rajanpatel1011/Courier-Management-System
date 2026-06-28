package com.courier.management.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record TrackingStatusDto(
        String consignmentNo,
        String status,
        String shipperName,
        String receiverName,
        String mode,
        String pickUpDate,
        String pickUpTime,
        LocalDate bookingDate,
        String currentLocation,
        String estimatedDelivery,
        LocalDateTime lastUpdated,
        List<TrackingEventDto> events
) {
}
