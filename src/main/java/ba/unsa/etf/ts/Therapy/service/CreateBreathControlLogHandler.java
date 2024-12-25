package ba.unsa.etf.ts.Therapy.service;

import ba.unsa.etf.ts.Therapy.repository.StressReliefActionRepository;
import ba.unsa.etf.ts.Therapy.request.CreateBreathControlLogRequest;
import ba.unsa.etf.ts.Therapy.responses.CreateBreathControlLogResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ba.unsa.etf.ts.Therapy.models.*;
import ba.unsa.etf.ts.Therapy.mapper.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@Validated
public class CreateBreathControlLogHandler {
    @Autowired
    private CheckPatientExistenceHandler patientExistenceHandler;

    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;

    public ResponseEntity<CreateBreathControlLogResponse> handle(CreateBreathControlLogRequest request) {
        var patientExists = patientExistenceHandler.handle(request.getPatientId());
        if (!patientExists) {
            return ResponseEntity.notFound().build();
        }
        BreathControl action = new BreathControl();
        action.setTempo(request.getTempo());
        action.setPatientId(request.getPatientId());
        action.setStartedAt(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));
        BreathControl savedBreathControl = (BreathControl) stressReliefActionRepository.save(action);
        CreateBreathControlLogResponse response = new CreateBreathControlLogResponse(savedBreathControl);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
