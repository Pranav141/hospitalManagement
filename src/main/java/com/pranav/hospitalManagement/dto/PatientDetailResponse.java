package com.pranav.hospitalManagement.dto;

import com.pranav.hospitalManagement.enums.BloodGroupEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
public class PatientDetailResponse {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String gender;
    private LocalDateTime createdAt;
    private BloodGroupEnum bloodGroup;
    private InsuranceResponse insuranceResponse;
    private List<AppointmentSummary> appointmentSummaryList;
}
