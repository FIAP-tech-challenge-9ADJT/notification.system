package tech.challenge.notification.system.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import tech.challenge.notification.system.application.services.UserApplicationService;
import tech.challenge.notification.system.domain.valueobjects.UserId;
import tech.challenge.notification.system.infrastructure.persistence.entities.UserJpaEntity;
import tech.challenge.notification.system.presentation.dtos.user.CreateUserDTO;
import tech.challenge.notification.system.presentation.dtos.user.UpdateUserDTO;
import tech.challenge.notification.system.presentation.dtos.user.UserResponseDTO;
import tech.challenge.notification.system.presentation.mappers.UserDtoMapper;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
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

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateProfile(@Valid @RequestBody UpdateUserDTO dto,
                                                         @AuthenticationPrincipal UserJpaEntity authenticatedUser) {
        var user = userApplicationService.updateUser(
                UserId.of(authenticatedUser.getId()),
                dto.name(),
                dto.email()
        );
        return ResponseEntity.ok(UserDtoMapper.toResponseDto(user));
    }
}