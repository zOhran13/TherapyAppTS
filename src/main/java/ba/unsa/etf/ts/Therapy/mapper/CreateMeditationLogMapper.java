package ba.unsa.etf.ts.Therapy.mapper;

import ba.unsa.etf.ts.Therapy.models.Meditation;
import ba.unsa.etf.ts.Therapy.request.CreateMeditationLogRequest;
import ba.unsa.etf.ts.Therapy.responses.CreateMeditationLogResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


import java.util.UUID;

@Mapper
public interface CreateMeditationLogMapper {
    CreateMeditationLogMapper INSTANCE = Mappers.getMapper(CreateMeditationLogMapper.class);
    public abstract CreateMeditationLogResponse entityToResponse(Meditation source);
    public abstract Meditation requestToEntity(CreateMeditationLogRequest source);

    @AfterMapping
    default void setStressReliefActionId(CreateMeditationLogRequest source, @MappingTarget Meditation dest) {
        dest.setStressReliefActionId(UUID.randomUUID().toString());
    }
}