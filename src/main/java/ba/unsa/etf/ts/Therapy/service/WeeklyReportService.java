package ba.unsa.etf.ts.Therapy.service;

import ba.unsa.etf.ts.Therapy.models.*;
import ba.unsa.etf.ts.Therapy.dto.*;
import ba.unsa.etf.ts.Therapy.repository.*;
import ba.unsa.etf.ts.Therapy.exceptions.*;
import ba.unsa.etf.ts.Therapy.dto.Mapper.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeeklyReportService {

    private final WeeklyReportRepo weeklyReportRepo;
    private final PsychologistRepo psychologistRepo;
    private final PatientRepo patientRepo;
    private final SessionRepo sessionRepo;
    private final WeeklyReportMapper weeklyReportMapper;
    public WeeklyReportService(WeeklyReportRepo weeklyReportRepo, PsychologistRepo psychologistRepo, PatientRepo patientRepo,SessionRepo sessionRepo,WeeklyReportMapper weeklyReportMapper) {
        this.weeklyReportRepo = weeklyReportRepo;
        this.psychologistRepo = psychologistRepo;
        this.patientRepo = patientRepo;
        this.sessionRepo=sessionRepo;
        this.weeklyReportMapper=weeklyReportMapper;
    }

    public WeeklyReportDto createWeeklyReportForUser(@Valid WeeklyReportDto weeklyReportDto) {
        Optional<Psychologist> psychologistOptional = psychologistRepo.findByUserId(weeklyReportDto.getPsychologistId());
        Psychologist psychologist = psychologistOptional.orElseThrow(() -> new UserNotFound("Psychologist not found."));

        Optional<Patient> patientOptional = patientRepo.findById(weeklyReportDto.getPatientId());
        Patient patient = patientOptional.orElseThrow(() -> new UserNotFound("Patient not found"));

        WeeklyReport weeklyReport = new WeeklyReport(weeklyReportDto.getContent(), psychologist, patient);
        return weeklyReportMapper.toDto(weeklyReportRepo.save(weeklyReport));
    }
@Transactional
    public void deleteWeeklyReportForUser(String weeklyReportId) {
    try {
        weeklyReportRepo.deleteById(weeklyReportId);
    } catch (EmptyResultDataAccessException ex) {
        throw new WeeklyReportNotFound("Weekly report not found.");
    }  }

    public WeeklyReportDto getWeeklyReportForUser(String weeklyReportId) {
        WeeklyReport w=weeklyReportRepo.findById(weeklyReportId).orElseThrow(() -> new WeeklyReportNotFound("Weekly report not found"));;
        return weeklyReportMapper.toDto(w);
    }

    public List<WeeklyReportDto> getAllWeeklyReportsForPsychologist(String psychologistId) {
        Optional<Psychologist> psychologistOptional = psychologistRepo.findByUserId(psychologistId);
        Psychologist psychologist = psychologistOptional.orElseThrow(() -> new UserNotFound("Psychologist not found"));

        List<WeeklyReport>w=weeklyReportRepo.findByPsychologist(psychologist);
        return w.stream()
                .map(weeklyReportMapper::toDto)
                .collect(Collectors.toList());
    }

}

