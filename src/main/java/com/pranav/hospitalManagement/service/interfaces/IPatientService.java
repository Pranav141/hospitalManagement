package com.pranav.hospitalManagement.service.interfaces;

import com.pranav.hospitalManagement.dto.InsuranceRequest;
import com.pranav.hospitalManagement.dto.PatientRequest;
import com.pranav.hospitalManagement.dto.PatientResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IPatientService {

    PatientResponse getPatientById(Long id);

    PatientResponse createPatient(PatientRequest p);

    Boolean deletePatientById(Long id);


    PatientResponse updatePatientById(Long id, PatientRequest p);

    Page<PatientResponse> getPatients(int page, int size, String sortBy);

    PatientResponse linkInsuranceToPatient(Long id, @Valid InsuranceRequest insuranceRequest);


}
