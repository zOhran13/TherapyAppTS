package ba.unsa.etf.ts.Therapy.repository;


import ba.unsa.etf.ts.Therapy.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface QualityRateRepo extends JpaRepository<QualityRate, String> {
    Double findAverageQualityRateByPsychologist(Psychologist psychologist);

    boolean existsByPatientAndCreatedAtAfter(Patient patient, LocalDateTime date);
}