package tech.challenge.notification.system.presentation.mappers;

import tech.challenge.notification.system.domain.entities.Role;
import tech.challenge.notification.system.presentation.dtos.role.RoleResponseDTO;

public class RoleDtoMapper {
    
    public static RoleResponseDTO toResponseDto(Role role) {
        return new RoleResponseDTO(
            role.getId(),
            tech.challenge.notification.system.infrastructure.persistence.entities.RoleName.valueOf(role.getName().name())
        );
    }
}