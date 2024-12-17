package ba.unsa.etf.ts.Therapy.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class WeeklyReportDto {
    private String weeklyReportId;
    private String content;
    private String psychologistId;
    private String patientId;
    private LocalDateTime createdAt;

}

