package com.pranav.hospitalManagement.controller;

import com.pranav.hospitalManagement.dto.DoctorRequest;
import com.pranav.hospitalManagement.dto.DoctorDetailResponse;
import com.pranav.hospitalManagement.dto.DoctorSummary;
import com.pranav.hospitalManagement.service.interfaces.IDoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctor")
public class DoctorController {
    private final IDoctorService doctorServcie;
    @PostMapping("")
    public ResponseEntity<DoctorDetailResponse> createDoctor(@RequestBody @Valid DoctorRequest doctorRequest){
        DoctorDetailResponse response=doctorServcie.createDoctor(doctorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("")
    public ResponseEntity<Page<DoctorSummary>> getDoctor(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ){
        Page<DoctorSummary> response=doctorServcie.getDoctors(page,size,sortBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailResponse> getDoctorById(@PathVariable Long id){
        DoctorDetailResponse response=doctorServcie.getDoctorById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDetailResponse> updateDoctorById(@PathVariable long id, @RequestBody @Valid DoctorRequest doctorRequest){
        DoctorDetailResponse response = doctorServcie.updateDoctorById(id,doctorRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
