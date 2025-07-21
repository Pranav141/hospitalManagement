package com.pranav.hospitalManagement.controller;

import com.pranav.hospitalManagement.dto.InsuranceRequest;
import com.pranav.hospitalManagement.dto.InsuranceResponse;
import com.pranav.hospitalManagement.service.InsuranceService;
import com.pranav.hospitalManagement.service.interfaces.IInsuranceService;
import com.pranav.hospitalManagement.service.interfaces.IPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/insurance")
public class InsuranceController {
    private final IInsuranceService insuranceService;
    private final IPatientService patientService;
    @GetMapping("/{policyNumber}")
    public ResponseEntity<InsuranceResponse> getInsuranceByPolicyNumber(@PathVariable String policyNumber){
        InsuranceResponse response = insuranceService.getInsuranceByPolicyNumber(policyNumber);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/patient/{id}")
    public ResponseEntity<InsuranceResponse> getInsuranceByPatientId(@PathVariable Long id){
        InsuranceResponse response=insuranceService.getInsuracneByPatientId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceResponse> updateInsuranceById(@PathVariable Long id, @RequestBody @Valid InsuranceRequest insuranceRequest){
        InsuranceResponse response = insuranceService.updateInsuranceById(id,insuranceRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteInsuranceById(@PathVariable Long id){
        Boolean response = insuranceService.deleteInsuranceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
