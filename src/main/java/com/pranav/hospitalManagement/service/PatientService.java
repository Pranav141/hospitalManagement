package com.pranav.hospitalManagement.service;

import com.pranav.hospitalManagement.dto.*;
import com.pranav.hospitalManagement.entity.Appointment;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {
    private final PatientRepository patientRepository;
    private final InsuranceRepository insuranceRepository;
    private final IInsuranceService insuranceService;
    private final ModelMapper modelMapper;
    @Override
    public Page<PatientSummary> getPatients(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Patient> patientsPage = patientRepository.findAll(pageable);
        return patientsPage.map(patient -> modelMapper.map(patient, PatientSummary.class));
    }

    @Override
    public PatientDetailResponse linkInsuranceToPatient(Long id, InsuranceRequest insuranceRequest) {
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
        PatientDetailResponse response = modelMapper.map(patient, PatientDetailResponse.class);
        response.setInsuranceResponse(modelMapper.map(insurance, InsuranceResponse.class));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDetailResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient with id:-" + id + " Not Found")
        );

        PatientDetailResponse response = modelMapper.map(patient, PatientDetailResponse.class);

        if (patient.getInsurance() != null) {
            response.setInsuranceResponse(modelMapper.map(patient.getInsurance(), InsuranceResponse.class));
        }

        if (patient.getAppointments() != null && !patient.getAppointments().isEmpty()) {
            List<AppointmentSummary> appointmentSummaries = patient.getAppointments().stream()
                    .map(appointment -> {
                        AppointmentSummary dto = modelMapper.map(appointment, AppointmentSummary.class);
                        if (appointment.getDoctor() != null) {
                            dto.setPatientId(appointment.getPatient().getId());
                            dto.setDoctorId(appointment.getDoctor().getId());
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());
            response.setAppointmentSummaryList(appointmentSummaries);
        } else {
            response.setAppointmentSummaryList(Collections.emptyList());
        }

        return response;
    }

    @Override
    public PatientDetailResponse createPatient(PatientRequest p) {

        boolean isEmailPresent = patientRepository.existsByEmail(p.getEmail());
        if(isEmailPresent){
            throw new AlreadyExistException("Patient with email "+p.getEmail()+" already exists");
        }
        Patient patient= modelMapper.map(p,Patient.class);
        try {
            patient=patientRepository.save(patient);
            PatientDetailResponse patientDetailResponse = modelMapper.map(patient, PatientDetailResponse.class);
            return patientDetailResponse;
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
    public PatientDetailResponse updatePatientById(Long id, PatientRequest p) {
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
        return modelMapper.map(patient, PatientDetailResponse.class);
    }


}
