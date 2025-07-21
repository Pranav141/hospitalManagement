package com.pranav.hospitalManagement.repository;

import com.pranav.hospitalManagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query(value = "SELECT * FROM appointment a " +
                   "WHERE a.doctor_id = :doctorId " +
                   "AND a.status != 'CANCELLED' " +
                   "AND a.appointment_start_time < :endTime " +
                   "AND a.appointment_end_time > :startTime",
           nativeQuery = true)
    List<Appointment> findOverlappingAppointments(
            @Param("doctorId") Long doctorId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
