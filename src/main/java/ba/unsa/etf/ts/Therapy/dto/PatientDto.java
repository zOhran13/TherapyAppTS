package ba.unsa.etf.ts.Therapy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
@Getter
@Setter
public class PatientDto {
    private String userId;
    private Integer age;
    private String selectedPsychologistId;
}

