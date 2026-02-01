package tech.challenge.notification.system.presentation.dtos.role;

import tech.challenge.notification.system.infrastructure.persistence.entities.RoleName;

public record RoleResponseDTO(
        Long id,
        RoleName name
) {
}