package tech.challenge.notification.system.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import tech.challenge.notification.system.application.services.UserApplicationService;
import tech.challenge.notification.system.domain.usecases.admin.DeleteUserUseCase;
import tech.challenge.notification.system.domain.valueobjects.UserId;
import tech.challenge.notification.system.infrastructure.persistence.entities.UserJpaEntity;
import tech.challenge.notification.system.presentation.dtos.user.CreateUserDTO;
import tech.challenge.notification.system.presentation.dtos.user.UserResponseDTO;
import tech.challenge.notification.system.presentation.mappers.UserDtoMapper;

@RestController
@RequestMapping("/teacher")
public class UserController {

    private final UserApplicationService userApplicationService;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(UserApplicationService userApplicationService, DeleteUserUseCase deleteUserUseCase) {
        this.userApplicationService = userApplicationService;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @GetMapping
    public ResponseEntity<UserResponseDTO> getProfile(@AuthenticationPrincipal UserJpaEntity authenticatedUser) {
        var user = userApplicationService.findUser(UserId.of(authenticatedUser.getId()));
        return ResponseEntity.ok(UserDtoMapper.toResponseDto(user));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO dto) {
        var user = userApplicationService.createUser(
                dto.name(),
                dto.email(),
                dto.login(),
                dto.password()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDtoMapper.toResponseDto(user));
    }

    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(UserDtoMapper.toResponseDto(userApplicationService.findUser(UserId.of(id))));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUserUseCase.execute(UserId.of(id));
        return ResponseEntity.noContent().build();
    }
}