package com.pranav.hospitalManagement.service.interfaces;

import com.pranav.hospitalManagement.dto.InsuranceRequest;
import com.pranav.hospitalManagement.dto.PatientRequest;
import com.pranav.hospitalManagement.dto.PatientDetailResponse;
import com.pranav.hospitalManagement.dto.PatientSummary;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IPatientService {

    PatientDetailResponse getPatientById(Long id);

    PatientDetailResponse createPatient(PatientRequest p);

    Boolean deletePatientById(Long id);


    PatientDetailResponse updatePatientById(Long id, PatientRequest p);

    Page<PatientSummary> getPatients(int page, int size, String sortBy);

    PatientDetailResponse linkInsuranceToPatient(Long id, @Valid InsuranceRequest insuranceRequest);


}
