package ba.unsa.etf.ts.Therapy.service;


import ba.unsa.etf.ts.Therapy.dto.RoleDto;
import ba.unsa.etf.ts.Therapy.exceptions.RoleNotFoundException;
import ba.unsa.etf.ts.Therapy.mapper.RoleMapper;
import ba.unsa.etf.ts.Therapy.models.RoleEntity;

import ba.unsa.etf.ts.Therapy.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto addRole(RoleDto roleDto) {
        String roleName = roleDto.getName();
        if (roleRepository.existsByName(roleName)) {
            throw new IllegalArgumentException("Role with name '" + roleName + "' already exists.");
        }

        RoleEntity roleEntity = new RoleEntity(roleName, UUID.randomUUID().toString());
        RoleEntity savedRole = roleRepository.save(roleEntity);

        return RoleMapper.toRoleDto(savedRole);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<RoleEntity> roleEntities = (List<RoleEntity>) roleRepository.findAll();
        return roleEntities.stream()
                .map(RoleMapper::toRoleDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(String roleId) {
        var role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException("Role not found"));
        return RoleMapper.toRoleDto(role);
    }

}

