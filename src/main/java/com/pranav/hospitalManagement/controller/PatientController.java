package com.pranav.hospitalManagement.controller;

import com.pranav.hospitalManagement.dto.InsuranceRequest;
import com.pranav.hospitalManagement.dto.PatientRequest;
import com.pranav.hospitalManagement.dto.PatientDetailResponse;
import com.pranav.hospitalManagement.dto.PatientSummary;
import com.pranav.hospitalManagement.service.interfaces.IPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {
    private final IPatientService patientService;

    @GetMapping("")
    public ResponseEntity<Page<PatientSummary>> getPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ){
        Page<PatientSummary> patientResponses=patientService.getPatients(page,size,sortBy);
         ResponseEntity<Page<PatientSummary>> response= ResponseEntity.ok(patientResponses);
         return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailResponse> getPatientById(@PathVariable Long id){
        PatientDetailResponse patientDetailResponse = patientService.getPatientById(id);
        ResponseEntity<PatientDetailResponse> response= ResponseEntity.ok(patientDetailResponse);
        return response;
    }

    @PostMapping("")
    public ResponseEntity<PatientDetailResponse> createPatient(@RequestBody @Valid PatientRequest p){
        PatientDetailResponse p1 = patientService.createPatient(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(p1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePatientById(@PathVariable Long id){
        Boolean response=patientService.deletePatientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDetailResponse> updatePatientById(@PathVariable Long id, @RequestBody @Valid PatientRequest p){
        PatientDetailResponse response=patientService.updatePatientById(id,p);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{id}/insurance")
    public ResponseEntity<PatientDetailResponse>  linkInsuranceToPatient(@PathVariable Long id, @RequestBody @Valid InsuranceRequest insuranceRequest){
        PatientDetailResponse response = patientService.linkInsuranceToPatient(id,insuranceRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
