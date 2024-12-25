package ba.unsa.etf.ts.Therapy.repository;

import ba.unsa.etf.ts.Therapy.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepo extends JpaRepository<Patient, String> {
    Patient findByUserId(String userId); // Koristi naziv 'userId' iz modela

    @Query("SELECT p FROM Patient p WHERE p.userId = :userId")
    Patient findByUserIdPatient(@Param("userId") String userId);



}
