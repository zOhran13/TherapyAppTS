package ba.unsa.etf.ts.Therapy.controllers;

import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ba.unsa.etf.ts.Therapy.models.*;
import ba.unsa.etf.ts.Therapy.service.*;
import ba.unsa.etf.ts.Therapy.mapper.*;
import ba.unsa.etf.ts.Therapy.request.*;
import ba.unsa.etf.ts.Therapy.responses.*;



@RestController
@RequestMapping("/api/stressrelief")
public class StressReliefActionController {
    @Autowired
    private GetActionLogsHandler getActionLogsHandler;
    @Autowired
    private CreateMeditationLogHandler createMeditationLogHandler;
    @Autowired
    private CreateBreathControlLogHandler createBreathControlLogHandler;
    @Autowired
    private CreateWalkLogHandler createWalkLogHandler;
    @Autowired
    private UpdateActionDurationTimeHandler updateActionDurationTimeHandler;
    @Autowired
    private CheckPatientExistenceHandler checkPatientExistenceHandler;

    @GetMapping("/actionlogs")
    public ResponseEntity<GetActionLogsResponse> getActionLogs(@RequestParam String patientId, @RequestParam int daysOffset) {
        GetActionLogsRequest request = new GetActionLogsRequest(patientId, daysOffset);
        return getActionLogsHandler.handle(request);
    }
    @PostMapping("/meditation")
    public ResponseEntity<CreateMeditationLogResponse> createMeditationLog(@RequestBody @Valid CreateMeditationLogRequest request) {
        System.out.println("Handling meditation log creation for patientUserId: " + request.getPatientId());

        // Proverite da li pacijent postoji
        boolean patientExists = checkPatientExistenceHandler.handle(request.getPatientId());

        if (!patientExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Pozovite handler i prosledite dalje
        return createMeditationLogHandler.handle(request);
    }



    @PostMapping("/walk")
    public ResponseEntity<CreateWalkLogResponse> createWalkLog(@RequestBody @Valid CreateWalkLogRequest request) {
        return createWalkLogHandler.handle(request);
    }

    @PostMapping("/breathcontrol")
    public ResponseEntity<CreateBreathControlLogResponse> createBreathLog(@RequestBody @Valid CreateBreathControlLogRequest request) {
        return createBreathControlLogHandler.handle(request);
    }

    @PatchMapping("/durationtime")
    public ResponseEntity<UpdateActionDurationTimeResponse> updateActionDurationTime(@RequestBody @Valid UpdateActionDurationTimeRequest request) {
        return updateActionDurationTimeHandler.handle(request);
    }
}
