package com.birzeit.hospital_graphql.controllers;

import com.birzeit.hospital_graphql.entities.Appointment;
import com.birzeit.hospital_graphql.entities.Doctor;
import com.birzeit.hospital_graphql.entities.Patient;
import com.birzeit.hospital_graphql.services.LocalDateFormatter;
import com.birzeit.hospital_graphql.services.PatientService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Optional;

@Controller
public class PatientController {

    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @QueryMapping
    public List<Patient> allPatients() {
        return patientService.getPatients();
    }

    @QueryMapping
    public Optional<Patient> patientById(@Argument long id) {
        return patientService.getPatientById(id);
    }

    @MutationMapping
    public Patient addPatient(
            @Argument String name,
            @Argument String gender,
            @Argument String dob,
            @Argument String phoneNumber,
            @Argument String email) {
        return patientService.addPatient(
                Patient
                        .builder()
                        .name(name)
                        .gender(gender)
                        .dob(LocalDateFormatter.makeDateFromString(dob))
                        .phoneNumber(phoneNumber)
                        .email(email)
                        .build()
        );
    }

    @MutationMapping
    public Patient deletePatient(@Argument long id) {
        return patientService.deletePatientById(id);
    }

    @SchemaMapping
    public List<Appointment> appointments(Patient patient) {
        return patientService.getAppointmentHistory(patient.getId());
    }

}
