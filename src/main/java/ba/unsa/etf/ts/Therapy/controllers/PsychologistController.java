package ba.unsa.etf.ts.Therapy.controllers;

import ba.unsa.etf.ts.Therapy.dto.PsychologistDto;
import ba.unsa.etf.ts.Therapy.service.PsychologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/psychologists")
public class PsychologistController {

    private final PsychologistService psychologistService;

    @Autowired
    public PsychologistController(PsychologistService psychologistService) {
        this.psychologistService = psychologistService;
    }

    @PostMapping("/save")
    public ResponseEntity<PsychologistDto> savePsychologist(@RequestBody PsychologistDto psychologist) {
        PsychologistDto savedPsychologist = psychologistService.savePsychologist(psychologist);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPsychologist);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PsychologistDto>> getAllPsychologists() {
        List<PsychologistDto> psychologists = psychologistService.getAllPsychologists();
        return ResponseEntity.ok(psychologists);
    }

    @GetMapping("/find/{psychologistId}")
    public ResponseEntity<PsychologistDto> getPsychologistById(@PathVariable String psychologistId) {
        PsychologistDto psychologist = psychologistService.getPsychologistById(psychologistId);
        return ResponseEntity.ok(psychologist);
    }

    @DeleteMapping("/delete/{psychologistId}")
    public ResponseEntity<String> deletePsychologist(@PathVariable UUID psychologistId) {
        psychologistService.deletePsychologist(psychologistId.toString());
        return ResponseEntity.ok("Psychologist deleted successfully.");
    }

    private Map<String, Object> createErrorResponse(String status, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("errors", createErrorDetails(message));
        return errorResponse;
    }

    private Map<String, Object> createErrorDetails(String message) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", message);
        errorDetails.put("details", message);
        return errorDetails;
    }
}
