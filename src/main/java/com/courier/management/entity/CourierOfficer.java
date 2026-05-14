package com.courier.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_courier_officers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierOfficer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    private Long id;

    @NotBlank(message = "Officer name is required")
    @Size(max = 40, message = "Officer name must not exceed 40 characters")
    @Column(name = "officer_name", nullable = false, length = 40)
    private String officerName;

    @NotBlank(message = "Password is required")
    @Size(max = 40, message = "Password must not exceed 40 characters")
    @Column(name = "off_pwd", nullable = false, length = 40)
    private String password;

    @NotBlank(message = "Address is required")
    @Size(max = 250, message = "Address must not exceed 250 characters")
    @Column(name = "address", nullable = false, length = 250)
    private String address;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(max = 12, message = "Phone number must not exceed 12 characters")
    @Column(name = "ph_no", nullable = false, length = 12)
    private String phoneNumber;

    @NotBlank(message = "Office is required")
    @Size(max = 100, message = "Office must not exceed 100 characters")
    @Column(name = "office", nullable = false, length = 100)
    private String office;

    @NotNull(message = "Registration date is required")
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime registrationDate;
}