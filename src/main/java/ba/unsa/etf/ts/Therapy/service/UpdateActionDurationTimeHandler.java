package ba.unsa.etf.ts.Therapy.service;

import ba.unsa.etf.ts.Therapy.models.StressReliefAction;
import ba.unsa.etf.ts.Therapy.repository.StressReliefActionRepository;
import ba.unsa.etf.ts.Therapy.request.UpdateActionDurationTimeRequest;
import ba.unsa.etf.ts.Therapy.responses.UpdateActionDurationTimeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UpdateActionDurationTimeHandler {
    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;
    public ResponseEntity<UpdateActionDurationTimeResponse> handle(UpdateActionDurationTimeRequest request) {
        Optional<StressReliefAction> entity = stressReliefActionRepository.findById(request.getStressReliefActionId());
        if (entity.isEmpty()) {
            throw new IllegalArgumentException("Action with provided id does not exist");
        }
        StressReliefAction action = entity.get();
        action.setDurationTime(request.getDurationTime());
        stressReliefActionRepository.save(action);
        return ResponseEntity.accepted().body(new UpdateActionDurationTimeResponse());
    }
}
