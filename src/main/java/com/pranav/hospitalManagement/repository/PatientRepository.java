package com.pranav.hospitalManagement.repository;

import com.pranav.hospitalManagement.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    boolean existsByEmail(String email);

}
