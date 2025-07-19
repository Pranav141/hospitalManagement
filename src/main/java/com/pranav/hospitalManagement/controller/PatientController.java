package com.pranav.hospitalManagement.controller;

import com.pranav.hospitalManagement.dto.PatientRequest;
import com.pranav.hospitalManagement.dto.PatientResponse;
import com.pranav.hospitalManagement.service.interfaces.IPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {
    private final IPatientService patientService;

    @GetMapping("")
    public ResponseEntity<List<PatientResponse>> getPatients(){
         List<PatientResponse> patientResponses=patientService.getPatients();
         ResponseEntity<List<PatientResponse>> response= ResponseEntity.ok(patientResponses);
         return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id){
        PatientResponse patientResponse= patientService.getPatientyId(id);
        ResponseEntity<PatientResponse> response= ResponseEntity.ok(patientResponse);
        return response;
    }

    @PostMapping("")
    public ResponseEntity<PatientResponse> createPatient(@RequestBody @Valid PatientRequest p){
        PatientResponse p1 = patientService.createPatient(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(p1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePatientById(@PathVariable Long id){
        Boolean response=patientService.deletePatientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatientById(@PathVariable Long id,@RequestBody @Valid PatientRequest p){
        PatientResponse response=patientService.updatePatientyId(id,p);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
