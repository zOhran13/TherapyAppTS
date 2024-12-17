package ba.unsa.etf.ts.Therapy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionDto {
    private String sessionId;
    private String psychologistId;
    private String patientId;
    private String day;
    private String time;

}
