package com.birzeit.hospital_graphql.services;

import com.birzeit.hospital_graphql.entities.*;
import com.birzeit.hospital_graphql.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(long id) {
        return patientRepository.findById(id);
    }

    public List<Appointment> getAppointmentHistory(long patientId) {
        Optional<Patient> patient = getPatientById(patientId);
        if(patient.isEmpty()) {
            throw new NoSuchElementException("No patient exists with the given id");
        }
        return patient
                .get()
                .getAppointments()
                .stream()
                .filter(appointment -> appointment.getAppointmentStatus().equals(AppointmentStatus.completed))
                .collect(Collectors.toList());
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient deletePatientById(long id) {
        Optional<Patient> patient = getPatientById(id);
        if(patient.isPresent()) {
            patientRepository.delete(patient.get());
            return patient.get();
        }
        return null;
    }

}
