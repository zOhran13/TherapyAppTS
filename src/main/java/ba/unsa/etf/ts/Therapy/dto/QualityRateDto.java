package ba.unsa.etf.ts.Therapy.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class QualityRateDto {
    private String qualityRateId;
    private String psychologistId;
    private String patientId;
    private Integer rate;
    private LocalDateTime createdAt;
}
