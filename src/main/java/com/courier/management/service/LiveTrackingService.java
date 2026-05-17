package com.courier.management.service;

import com.courier.management.dto.TrackingEventDto;
import com.courier.management.dto.TrackingStatusDto;
import com.courier.management.entity.Courier;
import com.courier.management.entity.CourierTrack;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LiveTrackingService {

    private final CourierService courierService;
    private final CourierTrackService courierTrackService;

    @Transactional(readOnly = true)
    public Optional<TrackingStatusDto> getTrackingStatus(String consignmentNo) {
        return courierService.getCourierByConsignmentNo(consignmentNo.trim())
            .map(this::toStatusDto);
    }

    private TrackingStatusDto toStatusDto(Courier courier) {
        List<CourierTrack> history = courierTrackService.getTrackingByConsignmentNo(courier.getConsignmentNo());
        List<TrackingEventDto> events = history.stream()
            .map(t -> new TrackingEventDto(
                t.getBookingTime(),
                t.getCurrentCity(),
                t.getStatus(),
                t.getComments()))
            .toList();

        String currentLocation = history.isEmpty()
            ? "Awaiting first scan"
            : history.getFirst().getCurrentCity();

        LocalDateTime lastUpdated = history.isEmpty()
            ? courier.getBookingDate().atStartOfDay()
            : history.getFirst().getBookingTime();

        return new TrackingStatusDto(
            courier.getConsignmentNo(),
            courier.getStatus(),
            courier.getShipperName(),
            courier.getReceiverName(),
            courier.getMode(),
            courier.getPickUpDate(),
            courier.getPickUpTime(),
            courier.getBookingDate(),
            currentLocation,
            estimateDelivery(courier),
            lastUpdated,
            events
        );
    }

    private String estimateDelivery(Courier courier) {
        if ("Delivered".equalsIgnoreCase(courier.getStatus())) {
            return "Delivered";
        }
        LocalDate estimate = courier.getBookingDate().plusDays(estimateDays(courier.getMode()));
        return estimate.toString();
    }

    private int estimateDays(String mode) {
        if (mode == null) {
            return 5;
        }
        return switch (mode.toLowerCase()) {
            case "air" -> 2;
            case "train" -> 4;
            case "road" -> 5;
            default -> 5;
        };
    }
}
