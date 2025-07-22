package com.pranav.hospitalManagement.service.interfaces;

import com.pranav.hospitalManagement.dto.AppointmentRequest;
import com.pranav.hospitalManagement.dto.AppointmentResponse;
import com.pranav.hospitalManagement.enums.AppointmentStatus;
import org.springframework.stereotype.Service;

@Service
public interface IAppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest);


    AppointmentResponse updateAppointmentStatus(Long id, AppointmentStatus status);

    void cancelAppointment(Long id);
}
