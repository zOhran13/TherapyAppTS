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
}
