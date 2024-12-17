package ba.unsa.etf.ts.Therapy.service;



import ba.unsa.etf.ts.Therapy.dto.RoleDto;

import java.util.List;

public interface RoleService {
     RoleDto addRole(RoleDto roleDto);
     List<RoleDto> getAllRoles();
     RoleDto getRoleById(String roleId);

}
