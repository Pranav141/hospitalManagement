package com.pranav.hospitalManagement.service.interfaces;

import com.pranav.hospitalManagement.dto.PatientRequest;
import com.pranav.hospitalManagement.dto.PatientResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IPatientService {
    List<PatientResponse> getPatients();

    PatientResponse getPatientyId(Long id);

    PatientResponse createPatient(PatientRequest p);

    Boolean deletePatientById(Long id);


    PatientResponse updatePatientyId(Long id, PatientRequest p);
}
