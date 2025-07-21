package com.pranav.hospitalManagement.dto;

import com.pranav.hospitalManagement.enums.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AppointmentSummary {
    private Long id;
    private LocalDateTime appointmentStartTime;
    private AppointmentStatus appointmentStatus;
    private LocalDateTime appointmentEndTime;
    private Long duration;
    private String reason;
    private Long patientId;
    private Long doctorId;
}
