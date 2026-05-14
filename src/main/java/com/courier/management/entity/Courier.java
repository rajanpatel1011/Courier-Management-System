package com.courier.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_courier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    private Long id;

    @NotBlank(message = "Consignment number is required")
    @Size(max = 20, message = "Consignment number must not exceed 20 characters")
    @Column(name = "cons_no", nullable = false, length = 20)
    private String consignmentNo;

    @NotBlank(message = "Shipper name is required")
    @Size(max = 100, message = "Shipper name must not exceed 100 characters")
    @Column(name = "ship_name", nullable = false, length = 100)
    private String shipperName;

    @NotBlank(message = "Shipper phone is required")
    @Size(max = 12, message = "Shipper phone must not exceed 12 characters")
    @Column(name = "phone", nullable = false, length = 12)
    private String shipperPhone;

    @NotBlank(message = "Shipper address is required")
    @Size(max = 200, message = "Shipper address must not exceed 200 characters")
    @Column(name = "s_add", nullable = false, length = 200)
    private String shipperAddress;

    @NotBlank(message = "Receiver name is required")
    @Size(max = 100, message = "Receiver name must not exceed 100 characters")
    @Column(name = "rev_name", nullable = false, length = 100)
    private String receiverName;

    @NotBlank(message = "Receiver phone is required")
    @Size(max = 12, message = "Receiver phone must not exceed 12 characters")
    @Column(name = "r_phone", nullable = false, length = 12)
    private String receiverPhone;

    @NotBlank(message = "Receiver address is required")
    @Size(max = 200, message = "Receiver address must not exceed 200 characters")
    @Column(name = "r_add", nullable = false, length = 200)
    private String receiverAddress;

    @NotBlank(message = "Shipment type is required")
    @Size(max = 40, message = "Shipment type must not exceed 40 characters")
    @Column(name = "type", nullable = false, length = 40)
    private String type;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.1", message = "Weight must be greater than 0")
    @Column(name = "weight", nullable = false)
    private Double weight;

    @NotBlank(message = "Invoice number is required")
    @Size(max = 20, message = "Invoice number must not exceed 20 characters")
    @Column(name = "invice_no", nullable = false, length = 20)
    private String invoiceNo;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "qty", nullable = false)
    private Integer quantity;

    @NotBlank(message = "Booking mode is required")
    @Size(max = 20, message = "Booking mode must not exceed 20 characters")
    @Column(name = "book_mode", nullable = false, length = 20)
    private String bookingMode;

    @NotNull(message = "Freight is required")
    @DecimalMin(value = "0.0", message = "Freight must be non-negative")
    @Column(name = "freight", nullable = false)
    private Double freight;

    @NotBlank(message = "Mode is required")
    @Size(max = 20, message = "Mode must not exceed 20 characters")
    @Column(name = "mode", nullable = false, length = 20)
    private String mode;

    @NotBlank(message = "Pick up date is required")
    @Size(max = 20, message = "Pick up date must not exceed 20 characters")
    @Column(name = "pick_date", nullable = false, length = 20)
    private String pickUpDate;

    @NotBlank(message = "Pick up time is required")
    @Size(max = 10, message = "Pick up time must not exceed 10 characters")
    @Column(name = "pick_time", nullable = false, length = 10)
    private String pickUpTime;

    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status must not exceed 20 characters")
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Size(max = 250, message = "Comments must not exceed 250 characters")
    @Column(name = "comments", length = 250)
    private String comments;

    @NotNull(message = "Booking date is required")
    @Column(name = "book_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;
}