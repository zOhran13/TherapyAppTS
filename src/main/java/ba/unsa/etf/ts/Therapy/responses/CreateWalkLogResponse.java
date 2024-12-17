package ba.unsa.etf.ts.Therapy.responses;

import ba.unsa.etf.ts.Therapy.models.Walk;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class CreateWalkLogResponse extends Walk {
}
