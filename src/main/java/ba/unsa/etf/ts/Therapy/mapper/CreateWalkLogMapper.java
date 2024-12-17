package ba.unsa.etf.ts.Therapy.mapper;

import ba.unsa.etf.ts.Therapy.models.Walk;
import ba.unsa.etf.ts.Therapy.request.CreateWalkLogRequest;
import ba.unsa.etf.ts.Therapy.responses.CreateWalkLogResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


import java.util.UUID;

@Mapper
public interface CreateWalkLogMapper {
    CreateWalkLogMapper INSTANCE = Mappers.getMapper(CreateWalkLogMapper.class);
    public abstract CreateWalkLogResponse entityToResponse(Walk source);
    public abstract Walk requestToEntity(CreateWalkLogRequest source);
    @AfterMapping
    default void setStressReliefActionId(CreateWalkLogRequest source, @MappingTarget Walk dest) {
        dest.setStressReliefActionId(UUID.randomUUID().toString());
    }
}
