package ba.unsa.etf.ts.Therapy.service;

import ba.unsa.etf.ts.Therapy.dto.Mapper.*;
import ba.unsa.etf.ts.Therapy.dto.PatientDto;
import ba.unsa.etf.ts.Therapy.exceptions.*;
import ba.unsa.etf.ts.Therapy.models.*;
import ba.unsa.etf.ts.Therapy.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepo patientRepo;
private final PatientMapper patientMapper;
private final PsychologistRepo psychologistRepo;
private final WeeklyReportRepo weeklyReportRepo;
private final SessionRepo sessionRepo;
    @Autowired
    public PatientService(PatientRepo patientRepo, PatientMapper p, PsychologistRepo psychologistRepo, WeeklyReportRepo weeklyReportRepo, SessionRepo sessionRepo) {
        this.patientRepo = patientRepo;
        this.patientMapper=p;
        this.psychologistRepo = psychologistRepo;
        this.weeklyReportRepo = weeklyReportRepo;
        this.sessionRepo = sessionRepo;
    }



    public PatientDto savePatient(@Valid PatientDto patientDto) {
         try{
        Patient patientEntity = patientMapper.fromDto(patientDto);
        Patient savedPatient = patientRepo.save(patientEntity);
        return patientMapper.toDto(savedPatient);}
         catch(DataIntegrityViolationException e){
             throw new UserExists("Patient with the same key already exists");
        }
    }

    public Optional<PatientDto> findById(String patientId) {
        Optional<Patient> patientOptional = patientRepo.findById(patientId);
        return patientOptional.map(patientMapper::toDto);
    }

    public List<PatientDto> findAllPatients() {
        List<Patient> patients = patientRepo.findAll();
        return patients.stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deletePatient(String patientId) {
        Optional<Patient> patientOptional = patientRepo.findById(patientId);
        if (patientOptional.isPresent()) {
            patientRepo.deleteById(patientId);
        } else {
            throw new UserNotFound("Patient not found");
        }
    }

    public boolean checkIfPatientHasChosenPsychologist(String patientUserId) {
        log.info("Checking psychologist for patient_user_id: {}", patientUserId);

        Patient patient = patientRepo.findByUserId(patientUserId);
        if (patient == null) {
            log.error("No patient found with patient_user_id: {}", patientUserId);
            throw new UserNotFound("Patient not found with patient_user_id: " + patientUserId);
        }

        log.info("Patient found: {}", patient);
        return patient.getSelectedPsychologistId() != null;
    }

    public Patient savePatient(Patient patient) {
        return patientRepo.save(patient);
    }

    public Patient findByUserIdPatient(String userId) {
        System.out.println("Searching for patient with userId: " + userId);
        return patientRepo.findByUserIdPatient(userId);
    }



    public List<PatientDto> getAllPatientsThatGotNoReport(String psychologistId) {
        List<Patient> patientsWithoutReport = new ArrayList<>();
        Optional<Psychologist> psychologistOptional = psychologistRepo.findByUserId(psychologistId);
        Psychologist psychologist = psychologistOptional.orElseThrow(() -> new UserNotFound("Psychologist not found"));
        List<Session> sessions = sessionRepo.findByPsychologist(psychologist);
        LocalDateTime currentDate = LocalDateTime.now();

        for (Session session : sessions) {
            String time = session.getTime();
            String day = session.getDay();

            LocalTime desiredTime = LocalTime.parse(time);
            DayOfWeek desiredDayOfWeek = DayOfWeek.valueOf(day.toUpperCase());

            int daysToSubtract = currentDate.getDayOfWeek().getValue() - desiredDayOfWeek.getValue();
            LocalDateTime latestSessionDay = currentDate.minusDays(daysToSubtract);
            LocalDateTime latestSessionDateTime = latestSessionDay.withHour(desiredTime.getHour())
                    .withMinute(desiredTime.getMinute())
                    .withSecond(desiredTime.getSecond());
            boolean reportExists = weeklyReportRepo.existsByPsychologistAndCreatedAtAfter(psychologist, latestSessionDateTime);
            if (!reportExists) {
                patientRepo.findById(session.getPatientId()).ifPresent(patientsWithoutReport::add);
            }
        }

        return patientsWithoutReport.stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }
}
