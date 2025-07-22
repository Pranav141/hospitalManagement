package com.pranav.hospitalManagement.controller;

import com.pranav.hospitalManagement.dto.AppointmentRequest;
import com.pranav.hospitalManagement.dto.AppointmentResponse;
import com.pranav.hospitalManagement.enums.AppointmentStatus;
import com.pranav.hospitalManagement.service.interfaces.IAppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping("/{id}/status")
    public ResponseEntity<AppointmentResponse> updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam AppointmentStatus status) {
        AppointmentResponse response = appointmentService.updateAppointmentStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
