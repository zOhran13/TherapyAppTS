package ba.unsa.etf.ts.Therapy.repository;

import ba.unsa.etf.ts.Therapy.models.Psychologist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsychologistRepo extends JpaRepository<Psychologist,String> {

}
