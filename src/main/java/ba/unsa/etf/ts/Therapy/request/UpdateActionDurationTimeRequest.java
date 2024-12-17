package ba.unsa.etf.ts.Therapy.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class UpdateActionDurationTimeRequest {
    @NotBlank(message = "stressReliefActionId is required")
    private String stressReliefActionId;

    @Positive(message = "durationTime must be positive")
    private double durationTime;
}
