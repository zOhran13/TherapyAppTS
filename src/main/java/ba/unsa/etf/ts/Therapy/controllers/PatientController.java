package ba.unsa.etf.ts.Therapy.controllers;

import ba.unsa.etf.ts.Therapy.dto.Mapper.PatientMapper;
import ba.unsa.etf.ts.Therapy.dto.PatientDto;
import ba.unsa.etf.ts.Therapy.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @Autowired
    public PatientController(PatientService patientService, PatientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<PatientDto> savePatient(@RequestBody PatientDto patient) {
        PatientDto savedPatient = patientService.savePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
    }

    @GetMapping("/findUserPatient/{patientId}")
    public ResponseEntity<PatientDto> getUserPatientById(@PathVariable String patientId) {
        Optional<PatientDto> patient = patientService.findById(patientId);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/find/{patientId}")
    public ResponseEntity<PatientDto> findById(@PathVariable String patientId) {
        Optional<PatientDto> patient = patientService.findById(patientId);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientDto>> findAllPatients() {
        List<PatientDto> patients = patientService.findAllPatients();
        return ResponseEntity.ok(patients);
    }

    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<String> deletePatient(@PathVariable String patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.ok("Patient deleted successfully.");
    }

    @GetMapping("/checkIfPatientHasChosenPsychologist/{patientId}")
    public ResponseEntity<Boolean> checkIfPatientHasChosenPsychologist(@PathVariable String patientId) {
        boolean hasChosenPsychologist = patientService.checkIfPatientHasChosenPsychologist(patientId);
        return ResponseEntity.ok(hasChosenPsychologist);
    }

    @GetMapping("/getAllPatientsThatGotNoReport/{psychologistId}")
    public ResponseEntity<List<PatientDto>> getAllPatientsThatGotNoReport(@PathVariable String psychologistId) {
        List<PatientDto> patientsWithoutReport = patientService.getAllPatientsThatGotNoReport(psychologistId);
        return ResponseEntity.ok(patientsWithoutReport);
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
