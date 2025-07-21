package com.pranav.hospitalManagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class InsuranceRequest {
    @NotBlank(message = "Policy Number is required")
    private String policyNumber;

    @NotBlank(message = "Provider is required")
    private String provider;

    @NotNull(message = "Valid Until Date is required")
    @Future
    private LocalDate validUntil;
}
