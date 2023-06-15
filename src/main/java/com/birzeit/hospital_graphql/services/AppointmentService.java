package com.birzeit.hospital_graphql.services;

import com.birzeit.hospital_graphql.entities.Appointment;
import com.birzeit.hospital_graphql.entities.AppointmentStatus;
import com.birzeit.hospital_graphql.entities.Doctor;
import com.birzeit.hospital_graphql.entities.Patient;
import com.birzeit.hospital_graphql.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    AppointmentService(AppointmentRepository appointmentRepository, PatientService patientService, DoctorService doctorService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public Optional<Appointment> getAppointmentById(long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentDetails(long id) {
        return getAppointmentById(id);
    }

    public Appointment addAppointment(long patientId, long doctorId, String date, String time, String reason) {
        Optional<Patient> patient = patientService.getPatientById(patientId);
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if(patient.isPresent() && doctor.isPresent()) {
            Appointment appointment = Appointment
                    .builder()
                    .patient(patient.get())
                    .doctor(doctor.get())
                    .date(LocalDateFormatter.makeDateFromString(date))
                    .time(LocalDateFormatter.makeTimeFromString(time))
                    .reason(reason)
                    .appointmentStatus(AppointmentStatus.not_completed)
                    .build();
            appointmentRepository.save(appointment);
            return appointment;
        }
        return null;
    }

    public Appointment deleteAppointmentById(long id) {
        Optional<Appointment> appointment = getAppointmentById(id);
        if(appointment.isPresent()) {
            appointmentRepository.delete(appointment.get());
            return appointment.get();
        }
        return null;
    }

    public Optional<Appointment> cancelAppointment(long id) {
        Optional<Appointment> appointment = getAppointmentById(id);
        if(appointment.isPresent()) {
            appointment.get().setAppointmentStatus(AppointmentStatus.canceled);
            appointmentRepository.save(appointment.get());
        }
        return appointment;
    }

    public Optional<Appointment> completeAppointment(long id) {
        Optional<Appointment> appointment = getAppointmentById(id);
        if(appointment.isPresent()) {
            appointment.get().setAppointmentStatus(AppointmentStatus.completed);
            appointmentRepository.save(appointment.get());
        }
        return appointment;
    }

//    public void rescheduleAppointment(long id, String newDateTime) throws IOException, NoSuchElementException{
//        Optional<Appointment> appointment = getAppointmentById(id);
//        if(appointment.isEmpty())
//            throw new NoSuchElementException("No appointment exists with the given id");
//        LocalDate newDate;
//        LocalTime newTime;
//        try {
//            newDate = LocalDateFormatter.makeDateFromString(newDateTime);
//            newTime = LocalDateFormatter.makeTimeFromString(newDateTime);
//        } catch (Exception e) {
//            throw new IOException("Please enter the date in the format \"yyyy/MM/dd\"" +
//                    " and the time in the format \"HH:mm\"");
//        }
//        if(newDate.isBefore(LocalDate.now()) ||
//                (LocalDate.now().isEqual(newDate) && newTime.isBefore(LocalTime.now())))
//            throw new IOException("Date and time must be in the future");
//        appointment.get().setDate(newDate);
//        appointment.get().setTime(newTime);
//        appointmentRepository.save(appointment.get());
//    }

}
