package com.pranav.hospitalManagement.repository;

import com.pranav.hospitalManagement.entity.Insurance;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance,Long> {

    boolean existsByPolicyNumber(@NotBlank(message = "Policy Number is required") String policyNumber);

    Insurance findByPolicyNumber(String policyNumber);
}
