package ba.unsa.etf.ts.Therapy.mapper;


import ba.unsa.etf.ts.Therapy.dto.RoleDto;
import ba.unsa.etf.ts.Therapy.models.RoleEntity;

public class RoleMapper {
    public static RoleDto toRoleDto(RoleEntity roleEntity) {
        return new RoleDto(
                roleEntity.getRoleId(),
                roleEntity.getName()
        );
    }

    public static RoleEntity toRoleEntity(RoleDto roleDto) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleDto.getName());
        return roleEntity;
    }
}

