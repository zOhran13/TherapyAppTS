package ba.unsa.etf.ts.Therapy.dto.Mapper;

import ba.unsa.etf.ts.Therapy.dto.PatientDto;
import ba.unsa.etf.ts.Therapy.models.*;
import ba.unsa.etf.ts.Therapy.repository.PsychologistRepo;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Component
public class PatientMapper {
    private final PsychologistRepo psychologistRepo;

    public PatientMapper(PsychologistRepo psychologistRepo) {
        this.psychologistRepo = psychologistRepo;
    }

    public PatientDto toDto(Patient patient) {
        if (patient == null) {
            return null;
        }

        PatientDto dto = new PatientDto();
        dto.setUserId(patient.getUserId());
        dto.setSelectedPsychologistId(patient.getSelectedPsychologistId());
        dto.setAge(patient.getAge());
        return dto;
    }

    public Patient fromDto(PatientDto patientDto) {
        if (patientDto == null) {
            return null;
        }

        Patient patient = new Patient();
        Psychologist psychologist=null;
        if(patientDto.getSelectedPsychologistId()!=null){
        psychologist = psychologistRepo.findById(patientDto.getSelectedPsychologistId())
                .orElseThrow(() -> new IllegalArgumentException("Psychologist not found."));}
        patient.setUserId(patientDto.getUserId());
        patient.setAge(patientDto.getAge());
        patient.setSelectedPsychologist(psychologist);
        return patient;
    }
}
