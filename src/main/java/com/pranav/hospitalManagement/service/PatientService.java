package com.pranav.hospitalManagement.service;

import com.pranav.hospitalManagement.dto.PatientRequest;
import com.pranav.hospitalManagement.dto.PatientResponse;
import com.pranav.hospitalManagement.entity.Patient;
import com.pranav.hospitalManagement.exception.AlreadyExistException;
import com.pranav.hospitalManagement.exception.ResourceNotFoundException;
import com.pranav.hospitalManagement.repository.PatientRepository;
import com.pranav.hospitalManagement.service.interfaces.IPatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<PatientResponse> getPatients() {
        List<Patient> patients= patientRepository.findAll();
        return patients
                .stream()
                .map(patient -> modelMapper.map(patient,PatientResponse.class))
                .toList();
    }

    @Override
    public PatientResponse getPatientyId(Long id) {
        Patient p =patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient with id:-"+id+" Not Found")
                );
        return modelMapper.map(p, PatientResponse.class);
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
    public PatientResponse updatePatientyId(Long id, PatientRequest p) {
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
