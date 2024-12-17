package ba.unsa.etf.ts.Therapy.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class CreateMeditationLogRequest {
    @NotBlank(message = "patientId is required")
    private String patientId;

    private boolean music;
}
