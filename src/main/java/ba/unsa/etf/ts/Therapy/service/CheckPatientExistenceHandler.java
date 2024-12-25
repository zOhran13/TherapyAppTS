package ba.unsa.etf.ts.Therapy.service;

import ba.unsa.etf.ts.Therapy.exceptions.UserNotFound;
import ba.unsa.etf.ts.Therapy.models.Patient;
import ba.unsa.etf.ts.Therapy.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CheckPatientExistenceHandler {
    @Autowired
    private PatientRepo patientRepo;

    public boolean handle(String patientUserId) {
        Patient patient = patientRepo.findByUserId(patientUserId);

        if (patient != null) {
            System.out.println("Found patient with id: " + patient.getId());
            return true;
        }

        System.out.println("Patient does not exist for userId: " + patientUserId);
        return false;
    }

}






