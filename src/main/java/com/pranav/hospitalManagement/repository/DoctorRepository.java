package com.pranav.hospitalManagement.repository;

import com.pranav.hospitalManagement.entity.Doctor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid format for email") String email);
}
