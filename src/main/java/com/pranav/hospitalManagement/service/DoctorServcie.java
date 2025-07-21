package com.pranav.hospitalManagement.service;

import com.pranav.hospitalManagement.dto.AppointmentSummary;
import com.pranav.hospitalManagement.dto.DoctorRequest;
import com.pranav.hospitalManagement.dto.DoctorDetailResponse;
import com.pranav.hospitalManagement.dto.DoctorSummary;
import com.pranav.hospitalManagement.entity.Appointment;
import com.pranav.hospitalManagement.entity.Doctor;
import com.pranav.hospitalManagement.exception.AlreadyExistException;
import com.pranav.hospitalManagement.exception.ResourceNotFoundException;
import com.pranav.hospitalManagement.repository.DoctorRepository;
import com.pranav.hospitalManagement.service.interfaces.IDoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServcie implements IDoctorService {
    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;
    @Override
    public DoctorDetailResponse createDoctor(DoctorRequest doctorRequest) {
        if(doctorRepository.existsByEmail(doctorRequest.getEmail())){
            throw new AlreadyExistException("Doctor with Email:-"+doctorRequest.getEmail()+" already exists.");
        }
        Doctor doctor=modelMapper.map(doctorRequest,Doctor.class);
        doctor=doctorRepository.save(doctor);
        return modelMapper.map(doctor, DoctorDetailResponse.class);
    }

    @Override
    public Page<DoctorSummary> getDoctors(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Doctor> doctorsPage = doctorRepository.findAll(pageable);
        return doctorsPage.map(doctor -> modelMapper.map(doctor, DoctorSummary.class));
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorDetailResponse getDoctorById(Long id) {
        Doctor doctor=doctorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Doctor with id:-"+id+" Not found"));
        DoctorDetailResponse response = modelMapper.map(doctor,DoctorDetailResponse.class);
        if (doctor.getAppointments() != null && !doctor.getAppointments().isEmpty()) {
            List<AppointmentSummary> appointmentSummaries = doctor.getAppointments().stream()
                    .map(appointment -> {
                        AppointmentSummary dto = modelMapper.map(appointment, AppointmentSummary.class);
                        if (appointment.getDoctor() != null) {
                            dto.setPatientId(appointment.getPatient().getId());
                            dto.setDoctorId(appointment.getDoctor().getId());
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());
            response.setAppointmentSummaryList(appointmentSummaries);
        }
        else {
            response.setAppointmentSummaryList(null);
        }
        return response;
    }

    @Override
    public DoctorDetailResponse updateDoctorById(long id, DoctorRequest doctorRequest) {
        Doctor doctor=doctorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Doctor with id:-"+id+" not found"));
        if (!doctor.getEmail().equals(doctorRequest.getEmail()) &&
                doctorRepository.existsByEmail(doctorRequest.getEmail())) {
            throw new AlreadyExistException("Email is already mapped to another doctor");
        }
        doctor.setSpecialization(doctorRequest.getSpecialization());
        doctor.setName(doctorRequest.getName());
        doctor.setEmail(doctorRequest.getEmail());
        doctor=doctorRepository.save(doctor);
        return modelMapper.map(doctor, DoctorDetailResponse.class);
    }
}
