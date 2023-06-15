package com.birzeit.hospital_graphql.controllers;

import com.birzeit.hospital_graphql.entities.Appointment;
import com.birzeit.hospital_graphql.entities.Doctor;
import com.birzeit.hospital_graphql.entities.Patient;
import com.birzeit.hospital_graphql.services.DoctorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class DoctorController {

    private final DoctorService doctorService;

    DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @QueryMapping
    public List<Doctor> allDoctors() {
        return doctorService.getAllDoctors();
    }

    @QueryMapping
    public Optional<Doctor> doctorById(@Argument long id) {
        return doctorService.getDoctorById(id);
    }

    @MutationMapping
    public Doctor addDoctor(
            @Argument String name,
            @Argument String phoneNumber,
            @Argument String email,
            @Argument int medicalServiceId) {
        Doctor doctor = Doctor
                .builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
        return doctorService.addDoctor(doctor, medicalServiceId);
    }

    @MutationMapping
    public Doctor deleteDoctor(@Argument long id) {
        return doctorService.deleteDoctorById(id);
    }

    @MutationMapping
    public Doctor updateDoctor(
            @Argument long id,
            @Argument String name,
            @Argument String phoneNumber,
            @Argument String email) {
        return doctorService.updateDoctor(id, name, phoneNumber, email);
    }

    @SchemaMapping
    public List<Appointment> appointments(Doctor doctor) {
        return doctorService.getAllAppointments(doctor.getId());
    }

    @SchemaMapping
    public Doctor doctor(Appointment appointment) {
        return appointment.getDoctor();
    }

    @SchemaMapping
    public Patient patient(Appointment appointment) {
        return appointment.getPatient();
    }


}
