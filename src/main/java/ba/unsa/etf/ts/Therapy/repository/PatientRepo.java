package ba.unsa.etf.ts.Therapy.repository;

import ba.unsa.etf.ts.Therapy.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient,String> {

}