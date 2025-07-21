package com.pranav.hospitalManagement.service;

import com.pranav.hospitalManagement.dto.InsuranceRequest;
import com.pranav.hospitalManagement.dto.InsuranceResponse;
import com.pranav.hospitalManagement.dto.PatientRequest;
import com.pranav.hospitalManagement.dto.PatientResponse;
import com.pranav.hospitalManagement.entity.Insurance;
import com.pranav.hospitalManagement.entity.Patient;
import com.pranav.hospitalManagement.exception.AlreadyExistException;
import com.pranav.hospitalManagement.exception.ResourceNotFoundException;
import com.pranav.hospitalManagement.repository.InsuranceRepository;
import com.pranav.hospitalManagement.repository.PatientRepository;
import com.pranav.hospitalManagement.service.interfaces.IInsuranceService;
import com.pranav.hospitalManagement.service.interfaces.IPatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {
    private final PatientRepository patientRepository;
    private final InsuranceRepository insuranceRepository;
    private final IInsuranceService insuranceService;
    private final ModelMapper modelMapper;
    @Override
    public Page<PatientResponse> getPatients(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Patient> patientsPage = patientRepository.findAll(pageable);
        return patientsPage.map(patient -> modelMapper.map(patient, PatientResponse.class));
    }

    @Override
    public PatientResponse linkInsuranceToPatient(Long id, InsuranceRequest insuranceRequest) {
        if(!patientRepository.existsById(id)){
            throw new ResourceNotFoundException("Patient with id:-"+id+" Not Found");
        }
        if(insuranceRepository.existsByPolicyNumber(insuranceRequest.getPolicyNumber())){
            throw new AlreadyExistException("Insurance Policy with policy number:-"+insuranceRequest.getPolicyNumber()+" already exist");
        }
        Patient patient=patientRepository.findById(id).orElseThrow();
        Insurance insurance=insuranceService.createInsurance(insuranceRequest);
        patient.setInsurance(insurance);
        patient = patientRepository.save(patient);
        PatientResponse response = modelMapper.map(patient, PatientResponse.class);
        response.setInsuranceResponse(modelMapper.map(insurance, InsuranceResponse.class));
        return response;
    }

    @Override
    public PatientResponse getPatientById(Long id) {
        Patient p =patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient with id:-"+id+" Not Found")
                );
        Insurance insurance= p.getInsurance();
        if(insurance!=null){
            InsuranceResponse insuranceResponse=modelMapper.map(insurance,InsuranceResponse.class);
            PatientResponse response=modelMapper.map(p, PatientResponse.class);
            response.setInsuranceResponse(insuranceResponse);
            return response;
        }
        PatientResponse response=modelMapper.map(p, PatientResponse.class);
        return response;
    }

    @Override
    public PatientResponse createPatient(PatientRequest p) {

        boolean isEmailPresent = patientRepository.existsByEmail(p.getEmail());
        if(isEmailPresent){
            throw new AlreadyExistException("Patient with email "+p.getEmail()+" already exists");
        }
        Patient patient= modelMapper.map(p,Patient.class);
        try {
            patient=patientRepository.save(patient);
            PatientResponse patientResponse= modelMapper.map(patient,PatientResponse.class);
            return patientResponse;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deletePatientById(Long id) {
        if(!patientRepository.existsById(id)){
            throw new ResourceNotFoundException("Patient with id:-"+id+" Not Found");
        }
        patientRepository.deleteById(id);
        return true;
    }

    @Override
    public PatientResponse updatePatientById(Long id, PatientRequest p) {
        if(!patientRepository.existsById(id)){
            throw new ResourceNotFoundException("Patient with id:-"+id+" Not Found");
        }
        Patient patient=patientRepository.findById(id).orElseThrow(() -> new  ResourceNotFoundException("Patient with id:-"+id+" Not Found"));
        patient.setBirthDate(p.getBirthDate());
        patient.setGender(p.getGender());
        patient.setName(p.getName());
        patient.setEmail(p.getEmail());
        patient.setBloodGroup(p.getBloodGroup());
        patient=patientRepository.save(patient);
        return modelMapper.map(patient,PatientResponse.class);
    }


}
