package ba.unsa.etf.ts.Therapy.dto.Mapper;
import ba.unsa.etf.ts.Therapy.dto.*;
import ba.unsa.etf.ts.Therapy.repository.*;
import ba.unsa.etf.ts.Therapy.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {
    private final PsychologistRepo psychologistService;
    private final PatientRepo patientService;

    public SessionMapper(PsychologistRepo psychologistService, PatientRepo patientService) {
        this.psychologistService = psychologistService;
        this.patientService = patientService;
    }

    public SessionDto toDto(Session session) {
        if (session == null) {
            return null;
        }

        SessionDto dto = new SessionDto();
        dto.setSessionId(session.getQualityRateId());
        dto.setPsychologistId(session.getPsychologist().getUserId());
        if (session.getPatient() != null) {
            dto.setPatientId(session.getPatient().getUserId());
        }
        dto.setTime(session.getTime());
        dto.setDay(session.getDay());
        return dto;
    }

    public Session fromDto(SessionDto sessionDto) {
        if (sessionDto == null) {
            return null;
        }

        Session session = new Session();
        session.setQualityRateId(sessionDto.getSessionId());
        Psychologist psychologist = psychologistService.findById(sessionDto.getPsychologistId()).orElse(null);
        Patient patient = patientService.findById(sessionDto.getPatientId()).orElse(null);
        session.setPsychologist(psychologist);
        session.setPatient(patient);
        session.setTime(sessionDto.getTime());
        session.setDay(sessionDto.getDay());
        return session;
    }
}

