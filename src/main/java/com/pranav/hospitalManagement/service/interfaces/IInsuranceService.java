package com.pranav.hospitalManagement.service.interfaces;

import com.pranav.hospitalManagement.dto.InsuranceRequest;
import com.pranav.hospitalManagement.dto.InsuranceResponse;
import com.pranav.hospitalManagement.entity.Insurance;
import org.springframework.stereotype.Service;

@Service
public interface IInsuranceService {

    Insurance createInsurance(InsuranceRequest insuranceRequest);

    InsuranceResponse getInsuranceByPolicyNumber(String policyNumber);

    InsuranceResponse getInsuracneByPatientId(Long id);

    InsuranceResponse updateInsuranceById(Long id,InsuranceRequest insuranceRequest);

    Boolean deleteInsuranceById(Long id);
}
