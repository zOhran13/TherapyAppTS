package ba.unsa.etf.ts.Therapy.dto.Mapper;


import ba.unsa.etf.ts.Therapy.dto.DailyReportDto;
import ba.unsa.etf.ts.Therapy.models.DailyReport;
import ba.unsa.etf.ts.Therapy.models.Patient;
import ba.unsa.etf.ts.Therapy.models.WeeklyReport;
import ba.unsa.etf.ts.Therapy.repository.PatientRepo;
import ba.unsa.etf.ts.Therapy.repository.WeeklyReportRepo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class DailyReportMapper {
    public final PatientRepo patientRepo;
    public final WeeklyReportRepo weeklyReportRepo;
    DailyReportMapper(PatientRepo patientRepo, WeeklyReportRepo weeklyReportRepo1){
        this.patientRepo=patientRepo;
        this.weeklyReportRepo = weeklyReportRepo1;
    }
    public DailyReportDto toDto(DailyReport dailyReport) {
        if (dailyReport == null) {
            return null;
        }
        DailyReportDto dto = new DailyReportDto();
        dto.setPatientId(dailyReport.getPatient().getUserId());
        if (dailyReport.getWeeklyReport() != null) {
            dto.setWeeklyReportId(dailyReport.getWeeklyReport().getWeeklyReportId());
        }
        dto.setContent(dailyReport.getContent());
        dto.setCreatedAt(dailyReport.getCreatedAt());
        dto.setDailyReportId(dailyReport.getDailyReportId());
        return dto;
    }
    public DailyReport fromDto(DailyReportDto dailyReportDto) {
        if (dailyReportDto == null) {
            return null;
        }

        DailyReport dailyReport = new DailyReport();
        dailyReport.setContent(dailyReportDto.getContent());
        dailyReport.setCreatedAt(dailyReportDto.getCreatedAt());
        Patient patient = patientRepo.findById(dailyReportDto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + dailyReportDto.getPatientId()));
        dailyReport.setPatient(patient);
        if (dailyReportDto.getDailyReportId() != null) {
            Optional<WeeklyReport> weeklyReport = weeklyReportRepo.findById(dailyReportDto.getWeeklyReportId());
            weeklyReport.ifPresent(dailyReport::setWeeklyReport);
        }
        return dailyReport;
    }
}

