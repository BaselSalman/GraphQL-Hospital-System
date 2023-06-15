package com.birzeit.hospital_graphql.controllers;

import com.birzeit.hospital_graphql.entities.Appointment;
import com.birzeit.hospital_graphql.entities.AppointmentStatus;
import com.birzeit.hospital_graphql.services.AppointmentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;

    AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @QueryMapping
    public List<Appointment> allAppointments() {
        return appointmentService.getAppointments();
    }

    @QueryMapping
    public Optional<Appointment> appointmentById(@Argument long id) {
        return appointmentService.getAppointmentById(id);
    }

    @MutationMapping
    public Appointment addAppointment(
            @Argument long patientId,
            @Argument long doctorId,
            @Argument String date,
            @Argument String time,
            @Argument String reason) {
        return appointmentService.addAppointment(patientId, doctorId, date, time, reason);
    }


    @MutationMapping
    public Appointment deleteAppointment(@Argument long id) {
        return appointmentService.deleteAppointmentById(id);
    }

    @MutationMapping
    public Optional<Appointment> cancelAppointment(@Argument long id) {
        return appointmentService.cancelAppointment(id);
    }

    @MutationMapping
    public Optional<Appointment> completeAppointment(@Argument long id) {
        return appointmentService.completeAppointment(id);
    }

    @SchemaMapping
    public String status(Appointment appointment) {
        return appointment.getAppointmentStatus().name();
    }

}
