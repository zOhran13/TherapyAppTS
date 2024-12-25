package ba.unsa.etf.ts.Therapy.responses;

import ba.unsa.etf.ts.Therapy.models.Walk;
import ba.unsa.etf.ts.Therapy.service.CreateWalkLogHandler;
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
public class CreateWalkLogResponse extends Walk {
    public CreateWalkLogResponse(Walk walk){
        super.setDurationTime(walk.getDurationTime());
        super.setStartedAt(walk.getStartedAt());
        super.setPatientId(walk.getPatientId());
        super.setStressReliefActionId(walk.getStressReliefActionId());
        super.setKilometers(walk.getKilometers());
    }
}
