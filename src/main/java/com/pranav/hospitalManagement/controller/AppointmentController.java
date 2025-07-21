package com.pranav.hospitalManagement.controller;

import com.pranav.hospitalManagement.dto.AppointmentRequest;
import com.pranav.hospitalManagement.dto.AppointmentResponse;
import com.pranav.hospitalManagement.service.interfaces.IAppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointment")
public class AppointmentController {
    private final IAppointmentService appointmentService;
    @PostMapping("")
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody @Valid AppointmentRequest appointmentRequest){
        AppointmentResponse response=appointmentService.createAppointment(appointmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
