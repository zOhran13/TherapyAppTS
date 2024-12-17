package ba.unsa.etf.ts.Therapy.dto;

import lombok.*;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String type;
    private String name;
    private String email;
    private String password;
    @Getter
    private String userId;
    private String roleId;
    private String imageUrl;
}
