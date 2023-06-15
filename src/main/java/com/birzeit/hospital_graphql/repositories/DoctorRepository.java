package com.birzeit.hospital_graphql.repositories;

import com.birzeit.hospital_graphql.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
