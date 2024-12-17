package ba.unsa.etf.ts.Therapy.repository;



import ba.unsa.etf.ts.Therapy.models.DailyReport;
import ba.unsa.etf.ts.Therapy.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyReportRepo extends JpaRepository<DailyReport, String> {

    List<DailyReport> findByPatientAndCreatedAtAfter(Patient patient, LocalDate date);
}
