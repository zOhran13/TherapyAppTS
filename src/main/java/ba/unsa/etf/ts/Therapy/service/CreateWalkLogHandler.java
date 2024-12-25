package ba.unsa.etf.ts.Therapy.service;

import ba.unsa.etf.ts.Therapy.mapper.CreateWalkLogMapper;
import ba.unsa.etf.ts.Therapy.repository.StressReliefActionRepository;
import ba.unsa.etf.ts.Therapy.models.*;
import ba.unsa.etf.ts.Therapy.request.CreateWalkLogRequest;
import ba.unsa.etf.ts.Therapy.responses.CreateWalkLogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class CreateWalkLogHandler {
    @Autowired
    private StressReliefActionRepository stressReliefActionRepository;
    @Autowired
    private CheckPatientExistenceHandler patientExistenceHandler;

    public ResponseEntity<CreateWalkLogResponse> handle(CreateWalkLogRequest request) {
        var patientExists = patientExistenceHandler.handle(request.getPatientId());
        if (!patientExists) {
            return ResponseEntity.notFound().build();
        }
        Walk action = new Walk();
        action.setPatientId(request.getPatientId());
        action.setKilometers(request.getKilometers());
        action.setStartedAt(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));
        Walk savedWalk = (Walk) stressReliefActionRepository.save(action);
        CreateWalkLogResponse response = new CreateWalkLogResponse(savedWalk);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
