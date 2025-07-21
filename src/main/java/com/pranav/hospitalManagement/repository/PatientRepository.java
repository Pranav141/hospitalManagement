package com.pranav.hospitalManagement.repository;

import com.pranav.hospitalManagement.dto.PatientResponse;
import com.pranav.hospitalManagement.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    boolean existsByEmail(String email);

}
