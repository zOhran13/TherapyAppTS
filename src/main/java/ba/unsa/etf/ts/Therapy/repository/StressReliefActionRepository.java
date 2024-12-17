package ba.unsa.etf.ts.Therapy.repository;

import ba.unsa.etf.ts.Therapy.models.StressReliefAction;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface StressReliefActionRepository extends JpaRepository<StressReliefAction, String> {
    public abstract List<StressReliefAction> findAllByPatientIdAndStartedAtAfter(String patientId, OffsetDateTime targetOffsetDateTime);
}
