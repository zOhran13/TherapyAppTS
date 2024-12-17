package ba.unsa.etf.ts.Therapy.repository;


import ba.unsa.etf.ts.Therapy.models.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WeeklyReportRepo extends JpaRepository<WeeklyReport,String> {
    List<WeeklyReport> findByPsychologist(Psychologist psychologist);

    boolean existsByPsychologistAndCreatedAtAfter(Psychologist psychologist, LocalDateTime latestSessionDateTime);
}
