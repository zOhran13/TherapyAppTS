package ba.unsa.etf.ts.Therapy.controllers;

import ba.unsa.etf.ts.Therapy.dto.DailyReportDto;
import ba.unsa.etf.ts.Therapy.service.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/daily-reports")
public class DailyReportController {

    private final DailyReportService dailyReportService;

    @Autowired
    public DailyReportController(DailyReportService dailyReportService) {
        this.dailyReportService = dailyReportService;
    }

    @GetMapping
    public ResponseEntity<List<DailyReportDto>> getDailyReports(@Valid @RequestParam String patientId) {
        List<DailyReportDto> dailyReports = dailyReportService.getDailyReports(patientId);
        return ResponseEntity.ok(dailyReports);
    }

    @PostMapping
    public ResponseEntity<?> createDailyReport(@Valid @RequestBody DailyReportDto dailyReportDto) {
        try {
            DailyReportDto dailyReport = dailyReportService.createDailyReport(dailyReportDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(dailyReport);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating daily report: " + e.getMessage());
        }
    }


    @PutMapping("/{dailyReportId}")
    public ResponseEntity<DailyReportDto> updateDailyReport(@PathVariable String dailyReportId, @Valid @RequestBody DailyReportDto dailyReportDTO) {
        dailyReportDTO.setDailyReportId(dailyReportId);
        DailyReportDto updatedDailyReport = dailyReportService.updateDailyReport(dailyReportDTO);
        return ResponseEntity.ok(updatedDailyReport);
    }

    @DeleteMapping("/{dailyReportId}")
    public ResponseEntity<Void> deleteDailyReport(@PathVariable String dailyReportId) {
        dailyReportService.deleteDailyReport(dailyReportId);
        return ResponseEntity.noContent().build();
    }
}
