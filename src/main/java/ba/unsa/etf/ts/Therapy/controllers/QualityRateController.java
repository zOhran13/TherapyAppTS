package ba.unsa.etf.ts.Therapy.controllers;

import ba.unsa.etf.ts.Therapy.dto.QualityRateDto;
import ba.unsa.etf.ts.Therapy.models.QualityRate;
import ba.unsa.etf.ts.Therapy.service.QualityRateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quality-rates")
public class QualityRateController {

    private final QualityRateService qualityRateService;

    public QualityRateController(QualityRateService qualityRateService) {
        this.qualityRateService = qualityRateService;
    }

    @GetMapping("/psychologist/{psychologistId}")
    public ResponseEntity<Double> getQualityRateForPsychologist(@PathVariable String psychologistId) {
        Double qualityRate = qualityRateService.getQualityRateForPsychologist(psychologistId);
        return ResponseEntity.ok(qualityRate);
    }

    @GetMapping("/user-rating/{userId}")
    public ResponseEntity<Boolean> didUserGiveRatingAfterLastSession(@PathVariable String userId) {
        boolean rated = qualityRateService.didUserGiveRatingAfterLastSession(userId);
        return ResponseEntity.ok(rated);
    }

    @PostMapping("/create")
    public ResponseEntity<QualityRate> createQualityRate(@RequestBody QualityRateDto qualityRateDto) {
        QualityRate qualityRate = qualityRateService.createQualityRate(qualityRateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(qualityRate);
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
