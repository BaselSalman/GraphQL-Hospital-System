package com.birzeit.hospital_graphql.controllers;


import com.birzeit.hospital_graphql.entities.Appointment;
import com.birzeit.hospital_graphql.entities.Doctor;
import com.birzeit.hospital_graphql.entities.MedicalService;
import com.birzeit.hospital_graphql.services.LocalDateFormatter;
import com.birzeit.hospital_graphql.services.MedicalServiceService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class MedicalServiceController {

    private final MedicalServiceService medicalServiceService;

    MedicalServiceController(MedicalServiceService medicalServiceService) {
        this.medicalServiceService = medicalServiceService;
    }

    @QueryMapping
    public List<MedicalService> allMedicalServices() {
        return medicalServiceService.getMedicalServices();
    }

    @QueryMapping
    public Optional<MedicalService> medicalServiceById(@Argument int id) {
        return medicalServiceService.getMedicalServiceById(id);
    }

    @MutationMapping
    public MedicalService addMedicalService(
            @Argument String name,
            @Argument String description,
            @Argument String estimatedDuration,
            @Argument double cost) {
        return medicalServiceService.addMedicalService(
                MedicalService
                        .builder()
                        .name(name)
                        .description(description)
                        .estimatedDuration(LocalDateFormatter.makeTimeFromString(estimatedDuration))
                        .cost(cost)
                        .build()
        );
    }

    @MutationMapping
    public MedicalService deleteMedicalService(@Argument int id) {
        return medicalServiceService.deleteMedicalServiceById(id);
    }

}
