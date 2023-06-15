package com.birzeit.hospital_graphql.repositories;

import com.birzeit.hospital_graphql.entities.MedicalService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Integer> {
    Optional<MedicalService> findByName(String medicalServiceName);
}
