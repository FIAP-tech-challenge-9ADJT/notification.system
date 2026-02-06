package tech.challenge.notification.system.presentation.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.challenge.notification.system.application.services.UserApplicationService;
import tech.challenge.notification.system.domain.usecases.admin.DeleteUserUseCase;
import tech.challenge.notification.system.domain.valueobjects.UserId;
import tech.challenge.notification.system.presentation.dtos.user.CreateUserDTO;
import tech.challenge.notification.system.presentation.dtos.user.UserResponseDTO;
import tech.challenge.notification.system.presentation.mappers.UserDtoMapper;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserApplicationService userService;
    private final DeleteUserUseCase deleteUserUseCase;

    public AdminController(UserApplicationService userService, DeleteUserUseCase deleteUserUseCase) {
        this.userService = userService;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createAdmin(@Valid @RequestBody CreateUserDTO dto) {
        var user = userService.createAdmin(dto.name(), dto.email(), dto.login(), dto.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDtoMapper.toResponseDto(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(UserDtoMapper.toResponseDto(userService.findUser(UserId.of(id))));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUserUseCase.execute(UserId.of(id));
        return ResponseEntity.noContent().build();
    }
}