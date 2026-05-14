package com.courier.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_courier_track")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Courier ID is required")
    @Column(name = "cid", nullable = false)
    private Long courierId;

    @NotBlank(message = "Consignment number is required")
    @Size(max = 20, message = "Consignment number must not exceed 20 characters")
    @Column(name = "cons_no", nullable = false, length = 20)
    private String consignmentNo;

    @NotBlank(message = "Current city is required")
    @Size(max = 100, message = "Current city must not exceed 100 characters")
    @Column(name = "current_city", nullable = false, length = 100)
    private String currentCity;

    @NotBlank(message = "Status is required")
    @Size(max = 30, message = "Status must not exceed 30 characters")
    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Size(max = 255, message = "Comments must not exceed 255 characters")
    @Column(name = "comments", length = 255)
    private String comments;

    @NotNull(message = "Booking time is required")
    @Column(name = "bk_time", nullable = false)
    private LocalDateTime bookingTime;

    // Relationship with Courier entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid", referencedColumnName = "cid", insertable = false, updatable = false)
    private Courier courier;
}