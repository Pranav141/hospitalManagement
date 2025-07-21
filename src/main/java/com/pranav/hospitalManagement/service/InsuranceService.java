package com.pranav.hospitalManagement.service;

import com.pranav.hospitalManagement.dto.InsuranceRequest;
import com.pranav.hospitalManagement.dto.InsuranceResponse;
import com.pranav.hospitalManagement.entity.Insurance;
import com.pranav.hospitalManagement.entity.Patient;
import com.pranav.hospitalManagement.exception.ResourceNotFoundException;
import com.pranav.hospitalManagement.repository.InsuranceRepository;
import com.pranav.hospitalManagement.repository.PatientRepository;
import com.pranav.hospitalManagement.service.interfaces.IInsuranceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService implements IInsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public Insurance createInsurance(InsuranceRequest insuranceRequest) {
        Insurance insurance= modelMapper.map(insuranceRequest,Insurance.class);
        insurance = insuranceRepository.save(insurance);
        return insurance;
    }

    @Override
    public InsuranceResponse getInsuranceByPolicyNumber(String policyNumber) {
        if(!insuranceRepository.existsByPolicyNumber(policyNumber)){
            throw new ResourceNotFoundException("Insurance Policy with policy number:-"+policyNumber+" already exist");
        }
        Insurance insurance= insuranceRepository.findByPolicyNumber(policyNumber);
        return modelMapper.map(insurance,InsuranceResponse.class);
    }

    @Override
    public InsuranceResponse getInsuracneByPatientId(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Patient with id:-"+id+" Not Found"));
        Insurance insurance= patient.getInsurance();
        if(insurance==null){
            throw new ResourceNotFoundException("No insurance found for patient with id: " + id);
        }
        return modelMapper.map(insurance,InsuranceResponse.class);
    }

    @Override
    public InsuranceResponse updateInsuranceById(Long id,InsuranceRequest insuranceRequest) {
        Insurance insurance = insuranceRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Insurance not found by id:-"+id));
        insurance.setProvider(insuranceRequest.getProvider());
        insurance.setPolicyNumber(insuranceRequest.getPolicyNumber());
        insurance.setValidUntil(insuranceRequest.getValidUntil());
        insurance=insuranceRepository.save(insurance);
        return modelMapper.map(insurance,InsuranceResponse.class);
    }

    @Override
    @Transactional
    public Boolean deleteInsuranceById(Long id) {
        Insurance insurance = insuranceRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Policy with id:-"+id+" Not found"));
        Patient patient=insurance.getPatient();
        if(patient!=null){
            patient.setInsurance(null);
            patientRepository.save(patient);
        }
        insuranceRepository.delete(insurance);
        return true;
    }

}
