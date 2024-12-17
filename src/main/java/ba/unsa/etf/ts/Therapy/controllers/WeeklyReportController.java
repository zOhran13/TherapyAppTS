package ba.unsa.etf.ts.Therapy.controllers;

import ba.unsa.etf.ts.Therapy.dto.WeeklyReportDto;
import ba.unsa.etf.ts.Therapy.service.WeeklyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weekly-reports")
public class WeeklyReportController {

    private final WeeklyReportService weeklyReportService;

    @Autowired
    public WeeklyReportController(WeeklyReportService weeklyReportService) {
        this.weeklyReportService = weeklyReportService;
    }

    @PostMapping("/create")
    public ResponseEntity<WeeklyReportDto> createWeeklyReportForUser(@RequestBody WeeklyReportDto weeklyReportDto) {
        WeeklyReportDto weeklyReport = weeklyReportService.createWeeklyReportForUser(weeklyReportDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(weeklyReport);
    }

    @DeleteMapping("/delete/{weeklyReportId}")
    public ResponseEntity<String> deleteWeeklyReportForUser(@PathVariable String weeklyReportId) {
        weeklyReportService.deleteWeeklyReportForUser(weeklyReportId);
        return ResponseEntity.ok("Weekly report deleted successfully.");
    }

    @GetMapping("/get/{weeklyReportId}")
    public ResponseEntity<WeeklyReportDto> getWeeklyReportForUser(@PathVariable String weeklyReportId) {
        WeeklyReportDto weeklyReport = weeklyReportService.getWeeklyReportForUser(weeklyReportId);
        return ResponseEntity.ok(weeklyReport);
    }

    @GetMapping("/psychologist/{psychologistId}")
    public ResponseEntity<List<WeeklyReportDto>> getAllWeeklyReportsForPsychologist(@PathVariable String psychologistId) {
        List<WeeklyReportDto> weeklyReports = weeklyReportService.getAllWeeklyReportsForPsychologist(psychologistId);
        return ResponseEntity.ok(weeklyReports);
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
