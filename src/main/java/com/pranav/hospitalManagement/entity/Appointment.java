package com.pranav.hospitalManagement.entity;

import com.pranav.hospitalManagement.enums.AppointmentStatus;
import com.pranav.hospitalManagement.enums.BloodGroupEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentStartTime;

    @Column(nullable = false)
    private LocalDateTime appointmentEndTime;

    @Column(nullable = false)
    private Long duration;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(length = 500)
    private String reason;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn( nullable = false) // patient is required and not nullable
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(nullable = false)
    private Doctor doctor;
}
