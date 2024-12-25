package ba.unsa.etf.ts.Therapy.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ba.unsa.etf.ts.Therapy.models.BreathControl;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class CreateBreathControlLogResponse extends BreathControl {
    public CreateBreathControlLogResponse(BreathControl breathControl){
        super.setDurationTime(breathControl.getDurationTime());
        super.setStartedAt(breathControl.getStartedAt());
        super.setPatientId(breathControl.getPatientId());
        super.setStressReliefActionId(breathControl.getStressReliefActionId());
        super.setTempo(breathControl.getTempo());
    }
}
