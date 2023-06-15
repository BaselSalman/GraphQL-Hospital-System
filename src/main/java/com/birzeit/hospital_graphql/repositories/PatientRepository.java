package com.birzeit.hospital_graphql.repositories;

import com.birzeit.hospital_graphql.entities.Doctor;
import com.birzeit.hospital_graphql.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
