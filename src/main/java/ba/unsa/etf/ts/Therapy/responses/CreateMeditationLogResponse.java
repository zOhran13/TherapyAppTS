package ba.unsa.etf.ts.Therapy.responses;

import ba.unsa.etf.ts.Therapy.models.Meditation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class CreateMeditationLogResponse extends Meditation {
public CreateMeditationLogResponse(Meditation meditation){
    super.setStartedAt(meditation.getStartedAt());
    super.setMusic(meditation.isMusic());
    super.setDurationTime(meditation.getDurationTime());
    super.setPatientId(meditation.getPatientId());
    super.setStressReliefActionId(meditation.getStressReliefActionId());
}
}
