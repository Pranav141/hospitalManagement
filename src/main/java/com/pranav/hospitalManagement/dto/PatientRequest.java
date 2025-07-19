package com.pranav.hospitalManagement.dto;

import com.pranav.hospitalManagement.enums.BloodGroupEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class PatientRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Birth date is required")
    @Past
    private LocalDate birthDate;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;


    private String gender;

    @NotNull(message = "Blood group is required")
    private BloodGroupEnum bloodGroup;
}
