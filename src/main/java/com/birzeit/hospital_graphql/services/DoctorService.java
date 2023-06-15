package com.birzeit.hospital_graphql.services;

import com.birzeit.hospital_graphql.entities.Appointment;
import com.birzeit.hospital_graphql.entities.AppointmentStatus;
import com.birzeit.hospital_graphql.entities.Doctor;
import com.birzeit.hospital_graphql.entities.MedicalService;
import com.birzeit.hospital_graphql.repositories.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final MedicalServiceService medicalServiceService;

    DoctorService(DoctorRepository doctorRepository, MedicalServiceService medicalServiceService) {
        this.doctorRepository = doctorRepository;
        this.medicalServiceService = medicalServiceService;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(long id) {
        return doctorRepository.findById(id);
    }

    public Doctor addDoctor(Doctor doctor, int medicalServiceId) {
        Optional<MedicalService> medicalService = medicalServiceService.getMedicalServiceById(medicalServiceId);
        if(medicalService.isPresent()) {
            doctor.setMedicalService(medicalService.get());
            return doctorRepository.save(doctor);
        }
        return null;
    }

    public Doctor deleteDoctorById(long id) {
        Optional<Doctor> doctor = getDoctorById(id);
        if(doctor.isPresent()) {
            doctorRepository.delete(doctor.get());
            return doctor.get();
        }
        return null;
    }

    public Doctor updateDoctor(long id, String name, String phoneNumber, String email) {
        Optional<Doctor> doctor = getDoctorById(id);
        if(doctor.isPresent()) {
            if(name != null)
                doctor.get().setName(name);
            if(email != null)
                doctor.get().setEmail(email);
            if(phoneNumber != null)
                doctor.get().setPhoneNumber(phoneNumber);
            return doctorRepository.save(doctor.get());
        }
        return null;
    }

    public List<Appointment> getUpcomingAppointments(long doctorId) {
        Optional<Doctor> doctor = getDoctorById(doctorId);
        if(doctor.isPresent()) {
            return doctor
                    .get()
                    .getAppointments()
                    .stream()
                    .filter(appointment -> appointment.getAppointmentStatus()
                            .equals(AppointmentStatus.not_completed))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<Appointment> getAllAppointments(long doctorId) {
        Optional<Doctor> doctor = getDoctorById(doctorId);
        if(doctor.isPresent()) {
            return doctor.get().getAppointments();
        }
        return null;
    }
}
