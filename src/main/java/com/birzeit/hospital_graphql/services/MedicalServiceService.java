package com.birzeit.hospital_graphql.services;

import com.birzeit.hospital_graphql.entities.Appointment;
import com.birzeit.hospital_graphql.entities.Doctor;
import com.birzeit.hospital_graphql.entities.MedicalService;
import com.birzeit.hospital_graphql.repositories.MedicalServiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalServiceService {

    private final MedicalServiceRepository medicalServiceRepository;

    MedicalServiceService(MedicalServiceRepository medicalServiceRepository) {
        this.medicalServiceRepository = medicalServiceRepository;
    }

    public List<MedicalService> getMedicalServices() {
        return medicalServiceRepository.findAll();
    }

    public Optional<MedicalService> getMedicalServiceById(int id) {
        return medicalServiceRepository.findById(id);
    }

//    public Optional<MedicalService> getMedicalServiceByName(String medicalServiceName) {
//        return medicalServiceRepository.findByName(medicalServiceName);
//    }

    public MedicalService addMedicalService(MedicalService medicalService) {
        return medicalServiceRepository.save(medicalService);
    }

    public MedicalService deleteMedicalServiceById(int id) {
        Optional<MedicalService> medicalService = getMedicalServiceById(id);
        if(medicalService.isPresent()) {
            medicalServiceRepository.delete(medicalService.get());
            return medicalService.get();
        }
        return null;
    }
}
