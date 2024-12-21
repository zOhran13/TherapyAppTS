package ba.unsa.etf.ts.Therapy.mapper;


import ba.unsa.etf.ts.Therapy.dto.UserDto;
import ba.unsa.etf.ts.Therapy.models.UserEntity;
import ba.unsa.etf.ts.Therapy.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private RoleRepository roleRepository;

    // Convert User JPA Entity into UserDto
    public UserDto mapToUserDto(UserEntity user){
        String roleId = user.getRole() != null ? user.getRole().getRoleId() : null;
        return new UserDto(
                user.getType(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getUserId(),
                roleId,
                user.getImageUrl()
        );
    }


    // Convert UserDto into User JPA Entity
    public UserEntity mapToUser(UserDto userDto){
        UserEntity user = new UserEntity(
                userDto.getType(),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getPassword(),
                roleRepository.findById(userDto.getRoleId()).orElse(null),
                userDto.getImageUrl()
        );
        return user;
    }
}