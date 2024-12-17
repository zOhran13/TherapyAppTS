package ba.unsa.etf.ts.Therapy.responses;

import ba.unsa.etf.ts.Therapy.models.StressReliefAction;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class GetActionLogsResponse {
    private List<StressReliefAction> stressReliefActionLogs;

    public GetActionLogsResponse(List<StressReliefAction> stressReliefActionLogs) {
        this.stressReliefActionLogs = stressReliefActionLogs;
    }
}
