package com.pranav.hospitalManagement.service;

import com.pranav.hospitalManagement.dto.*;
import com.pranav.hospitalManagement.entity.Appointment;
import com.pranav.hospitalManagement.entity.Doctor;
import com.pranav.hospitalManagement.entity.Patient;
import com.pranav.hospitalManagement.enums.AppointmentStatus;
import com.pranav.hospitalManagement.exception.AlreadyExistException;
import com.pranav.hospitalManagement.exception.ResourceNotFoundException;
import com.pranav.hospitalManagement.exception.TimeLimitExceed;
import com.pranav.hospitalManagement.repository.AppointmentRepository;
import com.pranav.hospitalManagement.repository.DoctorRepository;
import com.pranav.hospitalManagement.repository.PatientRepository;
import com.pranav.hospitalManagement.service.interfaces.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    @Override
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {
        Long patientId=appointmentRequest.getPatientId();
        Long doctorId=appointmentRequest.getDoctorId();

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()->new ResourceNotFoundException("Doctor with id:- "+doctorId+" not found"));
        Patient patient = patientRepository.findById(patientId).orElseThrow(()->new ResourceNotFoundException("Patient with id:- "+patientId+" not found"));
        LocalDateTime appointmentStartTime=appointmentRequest.getAppointmentStartTime();
        Long duration=appointmentRequest.getDuration();
        LocalDateTime appointmentEndTime=appointmentStartTime.plusMinutes(duration);
        if( appointmentEndTime.getHour()>18 || (appointmentEndTime.getHour()==18 && appointmentEndTime.getMinute()>0)){
            throw new TimeLimitExceed("End time exceeds 6 PM");
        }



        List<Appointment> overlappingAppointments = appointmentRepository.findOverlappingAppointments(
                doctorId, appointmentStartTime,appointmentEndTime
        );

        if (!overlappingAppointments.isEmpty()) {
            throw new AlreadyExistException(
                    "Doctor is not available at " + appointmentStartTime +
                            ". Appointment conflicts with existing schedule. Please choose a different time slot."
            );
        }
        Appointment appointment = new Appointment();
        appointment.setAppointmentStartTime(appointmentStartTime);
        appointment.setAppointmentEndTime(appointmentEndTime);
        appointment.setDuration(duration);
        appointment.setReason(appointmentRequest.getReason());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment = appointmentRepository.save(appointment);
        DoctorSummary doctorSummary=modelMapper.map(doctor, DoctorSummary.class);
        PatientSummary patientSummary =modelMapper.map(patient, PatientSummary.class);
        AppointmentResponse response = modelMapper.map(appointment, AppointmentResponse.class);
        response.setDoctorSummary(doctorSummary);
        response.setPatientSummary(patientSummary);
        return response;
    }

    @Override
    @Transactional
    public AppointmentResponse updateAppointmentStatus(Long id, AppointmentStatus newStatus) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Appointment with id: " + id + " not found")
        );


         if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
             throw new IllegalStateException("Cannot change status of a completed appointment.");
         }

        appointment.setStatus(newStatus);
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return modelMapper.map(updatedAppointment, AppointmentResponse.class);
    }

    @Override
    @Transactional
    public void cancelAppointment(Long id) {
        updateAppointmentStatus(id, AppointmentStatus.CANCELLED);
    }


}
