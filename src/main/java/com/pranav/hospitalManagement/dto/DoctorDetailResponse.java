package com.pranav.hospitalManagement.dto;

import lombok.Data;

import java.util.List;


@Data
public class DoctorDetailResponse {
    private Long id;
    private String name;
    private String specialization;
    private String email;
//    private Set<Department> departments = new HashSet<>();
    private List<AppointmentSummary> appointmentSummaryList;
}
