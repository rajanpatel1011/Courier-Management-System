package com.courier.management.dto;

import java.time.LocalDateTime;

public record TrackingEventDto(
        LocalDateTime time,
        String location,
        String status,
        String comments
) {
}
