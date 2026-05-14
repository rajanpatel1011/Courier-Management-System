package com.courier.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "tbl_offices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Office name is required")
    @Size(max = 100, message = "Office name must not exceed 100 characters")
    @Column(name = "off_name", nullable = false, length = 100)
    private String officeName;

    @NotBlank(message = "Address is required")
    @Size(max = 230, message = "Address must not exceed 230 characters")
    @Column(name = "address", nullable = false, length = 230)
    private String address;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Column(name = "ph_no", nullable = false, length = 20)
    private String phoneNumber;

    @NotBlank(message = "Office timing is required")
    @Size(max = 100, message = "Office timing must not exceed 100 characters")
    @Column(name = "office_time", nullable = false, length = 100)
    private String officeTiming;

    @NotBlank(message = "Contact person is required")
    @Size(max = 100, message = "Contact person must not exceed 100 characters")
    @Column(name = "contact_person", nullable = false, length = 100)
    private String contactPerson;
}