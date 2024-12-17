package ba.unsa.etf.ts.Therapy.dto.Mapper;


import ba.unsa.etf.ts.Therapy.dto.PsychologistDto;
import ba.unsa.etf.ts.Therapy.models.Psychologist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
public class PsychologistMapper {

    public PsychologistDto toDto(Psychologist psychologist) {
        if (psychologist == null) {
            return null;
        }

        PsychologistDto dto = new PsychologistDto();
        dto.setUserId(psychologist.getUserId());
        return dto;
    }

    public Psychologist fromDto(PsychologistDto psychologistDto) {
        if (psychologistDto == null) {
            return null;
        }

        Psychologist psychologist = new Psychologist();
        psychologist.setUserId(psychologistDto.getUserId());
        return psychologist;
    }
}

