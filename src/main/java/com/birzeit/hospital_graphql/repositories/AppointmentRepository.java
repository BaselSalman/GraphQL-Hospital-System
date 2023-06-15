package com.birzeit.hospital_graphql.repositories;

import com.birzeit.hospital_graphql.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
