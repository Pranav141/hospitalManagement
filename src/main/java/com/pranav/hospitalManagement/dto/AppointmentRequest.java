package com.pranav.hospitalManagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pranav.hospitalManagement.validation.ValidBusinessHours;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    @NotNull(message = "Appointment Time is required")
    @Future(message = "Appointment time must be in the future")
    @ValidBusinessHours
    private LocalDateTime appointmentStartTime;

    @NotNull
    @Min(message = "Duration must be at least 15 minutes", value = 15L) // Changed message
    @Max(message = "Duration must be at most 30 minutes", value = 30L) // Changed message
    private Long duration = 15L;

    @NotBlank(message="Reason is Required")
    private String reason;
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;
}
