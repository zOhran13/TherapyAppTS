package ba.unsa.etf.ts.Therapy.service;


import ba.unsa.etf.ts.Therapy.dto.*;
import ba.unsa.etf.ts.Therapy.exceptions.*;
import ba.unsa.etf.ts.Therapy.models.*;
import ba.unsa.etf.ts.Therapy.repository.PatientRepo;
import ba.unsa.etf.ts.Therapy.repository.PsychologistRepo;
import ba.unsa.etf.ts.Therapy.repository.QualityRateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QualityRateService {

    private final QualityRateRepo qualityRateRepo;
    private final SessionService sessionService;
    private final PatientRepo patientRepo;
    private final PsychologistRepo psychologistRepo;

    @Autowired
    public QualityRateService(QualityRateRepo qualityRateRepo, SessionService sessionService,PatientRepo patientRepo,PsychologistRepo psychologistRepo) {
        this.qualityRateRepo = qualityRateRepo;
        this.sessionService = sessionService;
        this.patientRepo=patientRepo;
        this.psychologistRepo=psychologistRepo;
    }

    public Double getQualityRateForPsychologist(String psychologistId) {
        Optional<Psychologist> p = psychologistRepo.findByUserId(psychologistId);
        Psychologist psychologist = p.orElseThrow(() -> new UserNotFound("Psychologist not found"));

        return qualityRateRepo.findAverageQualityRateByPsychologist(psychologist);
    }

    public boolean didUserGiveRatingAfterLastSession(String userId) {
        SessionDto session = sessionService.getUserSession(userId);
        String time=session.getTime();
        String day=session.getDay();
        LocalDateTime currentDate = LocalDateTime.now();
        LocalTime desiredTime = LocalTime.parse(time);

        DayOfWeek desiredDayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
        int daysToSubtract = currentDate.getDayOfWeek().getValue() - desiredDayOfWeek.getValue();

        LocalDateTime latestSessionDay = currentDate.minusDays(daysToSubtract);
        LocalDateTime latestSessionDateTime = latestSessionDay.withHour(desiredTime.getHour())
                .withMinute(desiredTime.getMinute())
                .withSecond(desiredTime.getSecond());
        Optional<Patient> patientOptional = patientRepo.findById(userId);
        Patient patient = patientOptional.orElseThrow(() -> new UserNotFound("Patient not found"));
        return qualityRateRepo.existsByPatientAndCreatedAtAfter(patient, latestSessionDateTime);
    }

    public QualityRate createQualityRate(@Valid QualityRateDto qualityRateDto) {
        SessionDto sessionOptional = sessionService.getUserSession(qualityRateDto.getPatientId());

        Optional<Patient> patientOptional = patientRepo.findById(qualityRateDto.getPatientId());
        Patient patient = patientOptional.orElseThrow(() -> new UserNotFound("Patient not found"));
        Optional<Psychologist> p = psychologistRepo.findByUserId(qualityRateDto.getPsychologistId());
        Psychologist psychologist = p.orElseThrow(() -> new UserNotFound("Psychologist not found"));
        QualityRate qualityRate = new QualityRate(psychologist, patient, qualityRateDto.getRate());
        return qualityRateRepo.save(qualityRate);
    }
}

