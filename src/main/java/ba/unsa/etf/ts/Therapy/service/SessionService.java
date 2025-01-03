package ba.unsa.etf.ts.Therapy.service;
import ba.unsa.etf.ts.Therapy.dto.*;
import ba.unsa.etf.ts.Therapy.models.*;
import ba.unsa.etf.ts.Therapy.repository.*;
import ba.unsa.etf.ts.Therapy.dto.Mapper.*;
import ba.unsa.etf.ts.Therapy.exceptions.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import java.io.IOException;
import java.io.InvalidClassException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.List;
import java.util.Optional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final SessionRepo sessionRepo;
    private final PsychologistRepo psychologistRepo;
    private final PatientRepo patientRepo;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final SessionMapper sessionMapper;
    @Autowired
    public SessionService(SessionRepo sessionRepo, PsychologistRepo psychologistRepo, PatientRepo patientRepo, SessionMapper sessionMapper) {
        this.sessionRepo = sessionRepo;
        this.psychologistRepo = psychologistRepo;
        this.patientRepo = patientRepo;
        this.sessionMapper=sessionMapper;
    }

    public SessionDto createSession(String psychologistId, String day, String time) {
        DayOfWeek sessionDay;
        LocalTime sessionTime;

        try {
            sessionDay = DayOfWeek.valueOf(day.toUpperCase()); // Convert input to DayOfWeek enum
        } catch (IllegalArgumentException e) {
            throw new InvalidFormat("Invalid day format.");
        }

        try {
            sessionTime = LocalTime.parse(time, timeFormatter);
        } catch (Exception e) {
            throw new InvalidFormat("Invalid time format.");
        }

        Optional<Psychologist> p = psychologistRepo.findByUserId(psychologistId);
        Psychologist psychologist = p.orElseThrow(() -> new UserNotFound("Psychologist not found"));
        Session session = new Session(psychologist, null, day, time);

        try {
            return sessionMapper.toDto(sessionRepo.save(session));
        } catch (Exception e) {
            throw new FailedToAdd("Failed to add session");
        }
    }

    public SessionDto addPatientToSession(String psychologistId, String day, String time, String patientId) {
        DayOfWeek sessionDay;
        LocalTime sessionTime;

        try {
            sessionDay = DayOfWeek.valueOf(day.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidFormat("Invalid day format");
        }

        try {
            sessionTime = LocalTime.parse(time, timeFormatter);
        } catch (Exception e) {
            throw new UserNotFound("Invalid time format");
        }
        Optional<Psychologist> p = psychologistRepo.findByUserId(psychologistId);
        Psychologist psychologist = p.orElseThrow(() -> new UserNotFound("Psychologist not found"));

        Optional<Session> sessionOptional = sessionRepo.findByPsychologistAndDayAndTimeAndPatientIsNull(psychologist, day, time);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();

            Optional<Patient> patientOptional = patientRepo.findById(patientId);
            Patient patient = patientOptional.orElseThrow(() -> new UserNotFound("Patient not found"));

            session.setPatient(patient);

            try {
                patient.setSelectedPsychologist(psychologist);
                patientRepo.save(patient);
                return sessionMapper.toDto(sessionRepo.save(session));
            } catch (Exception e) {
                throw new FailedToAdd("Failed to add patient to session");
            }
        } else {
            throw new SessionNotFound("Session not found for psychologist, date, and time");
        }
    }

    public List<SessionDto> getAllAvailableSessions(String psychologistId) {
        Optional<Psychologist> psychologistOptional = psychologistRepo.findByUserId(psychologistId);
        Psychologist psychologist = psychologistOptional.orElseThrow(() -> new UserNotFound("Psychologist not found"));

        List<Session> sessions = sessionRepo.findByPsychologist(psychologist);
        return sessions.stream()
                .map(sessionMapper::toDto)
                .collect(Collectors.toList());
    }
@Transactional
    public void deleteSessionByPsychologistIdAndDayAndTime(String psychologistId,String day, String time) {

        DayOfWeek sessionDay;
        LocalTime sessionTime;

        try {
            sessionDay = DayOfWeek.valueOf(day.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidFormat("Invalid day format");
        }

        try {
            sessionTime = LocalTime.parse(time, timeFormatter);
        } catch (Exception e) {
            throw new InvalidFormat("Invalid time format");
        }

        Optional<Psychologist> p = psychologistRepo.findByUserId(psychologistId);
        Psychologist psychologist = p.orElseThrow(() -> new UserNotFound("Psychologist not found"));

        sessionRepo.deleteByPsychologistAndDayAndTime(psychologist, day, time);
    }


    public void removeUserFromSession(String userId, String psychologistId) {
        sessionRepo.updatePatientIdByUserIdAndPsychologistId(userId, psychologistId);

    }

    public boolean isUserAlreadyScheduled(String userId) {
        Optional<Patient> patientOptional = patientRepo.findById(userId);
        Patient patient = patientOptional.orElseThrow(() -> new UserNotFound("Patient not found"));

        return sessionRepo.existsByPatient(patient);
    }

    public SessionDto updateSession(String psychologistId, String userId, String newDay, String newTime) {
        DayOfWeek sessionDay;
        LocalTime sessionTime;

        try {
            sessionDay = DayOfWeek.valueOf(newDay.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidFormat("Invalid day format");
        }

        try {
            sessionTime = LocalTime.parse(newTime, timeFormatter);
        } catch (Exception e) {
            throw new InvalidFormat( "Invalid time format");
        }
        Optional<Patient> patientOptional = patientRepo.findById(userId);
        Patient patient = patientOptional.orElseThrow(() -> new UserNotFound("Patient not found"));
        Optional<Psychologist> p = psychologistRepo.findById(psychologistId);
        Psychologist psychologist = p.orElseThrow(() -> new UserNotFound("Psychologist not found"));

        Optional<Session> sessionOptional = sessionRepo.findByPsychologistAndPatient(psychologist, patient);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            session.setDay(sessionDay.toString());
            session.setTime(newTime);
                return sessionMapper.toDto(sessionRepo.save(session));
        } else {
                throw new SessionNotFound("Session not found for psychologist and user");
        }
    }

    public List<SessionDto> getPsychologistSessions(String psychologistId) {
        Optional<Psychologist> p = psychologistRepo.findByUserId(psychologistId);
        Psychologist psychologist = p.orElseThrow(() -> new UserNotFound("Psychologist not found"));

        List<Session> sessions = sessionRepo.findByPsychologist(psychologist);
        return sessions.stream()
                .map(sessionMapper::toDto)
                .collect(Collectors.toList());
    }

    public SessionDto getUserSession(String userId) {
        Optional<Patient> patientOptional = patientRepo.findById(userId);
        Patient patient = patientOptional.orElseThrow(() -> new UserNotFound("Patient not found"));
        Optional<Session> s=sessionRepo.findByPatient(patient);
        Session session=s.orElseThrow(() -> new UserNotFound("Session not found."));
        return sessionMapper.toDto(session);
    }
}