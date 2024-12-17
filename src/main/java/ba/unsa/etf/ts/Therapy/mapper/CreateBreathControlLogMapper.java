package ba.unsa.etf.ts.Therapy.mapper;

import ba.unsa.etf.ts.Therapy.models.BreathControl;
import ba.unsa.etf.ts.Therapy.request.CreateBreathControlLogRequest;
import ba.unsa.etf.ts.Therapy.responses.CreateBreathControlLogResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


import java.util.UUID;

@Mapper
public interface CreateBreathControlLogMapper {
    CreateBreathControlLogMapper INSTANCE = Mappers.getMapper(CreateBreathControlLogMapper.class);
    public abstract CreateBreathControlLogResponse entityToResponse(BreathControl source);
    public abstract BreathControl requestToEntity(CreateBreathControlLogRequest source);
    @AfterMapping
    default void setStressReliefActionId(CreateBreathControlLogRequest source, @MappingTarget BreathControl dest) {
        dest.setStressReliefActionId(UUID.randomUUID().toString());
    }
}
