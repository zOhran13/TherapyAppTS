package ba.unsa.etf.ts.Therapy.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ValidateTokenRequestDto {
    private List<String> roles;
}
