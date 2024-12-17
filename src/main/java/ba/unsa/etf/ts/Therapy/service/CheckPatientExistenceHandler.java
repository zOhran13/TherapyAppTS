package ba.unsa.etf.ts.Therapy.service;

import ba.unsa.etf.ts.Therapy.exceptions.UserNotFound;
import ba.unsa.etf.ts.Therapy.models.Patient;
import ba.unsa.etf.ts.Therapy.repository.PatientRepo;
import org.springframework.stereotype.Service;

@Service
public class CheckPatientExistenceHandler {

    private final PatientRepo patientRepo;

    public CheckPatientExistenceHandler(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    public boolean handle(String patientId) {
        return patientRepo.findById(patientId).isPresent();
    }
}
