package com.pranav.hospitalManagement.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class InsuranceResponse{
    private Long id;
    private String policyNumber;
    private String provider;
    private LocalDate validUntil;

}