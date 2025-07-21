package com.pranav.hospitalManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Specialization is required")
    private String specialization;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid format for email")
    private String email;
}
