package com.pranav.hospitalManagement.service.interfaces;

import com.pranav.hospitalManagement.dto.DoctorRequest;
import com.pranav.hospitalManagement.dto.DoctorDetailResponse;
import com.pranav.hospitalManagement.dto.DoctorSummary;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IDoctorService {
    DoctorDetailResponse createDoctor(@Valid DoctorRequest doctorRequest);

    Page<DoctorSummary> getDoctors(int page, int size, String sortBy);


    DoctorDetailResponse getDoctorById(Long id);

    DoctorDetailResponse updateDoctorById(long id, @Valid DoctorRequest doctorRequest);
}
