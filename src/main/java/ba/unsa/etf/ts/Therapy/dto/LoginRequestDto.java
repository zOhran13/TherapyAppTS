package ba.unsa.etf.ts.Therapy.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
public class LoginRequestDto {
    private String email;

    private String password;
}
