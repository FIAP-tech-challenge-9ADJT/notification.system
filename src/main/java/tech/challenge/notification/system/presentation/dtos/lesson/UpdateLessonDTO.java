package tech.challenge.notification.system.presentation.dtos.lesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateLessonDTO(

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must have at most 100 characters")
        String name,

        @NotBlank(message = "Description is required")
        @Size(max = 200, message = "Description must have at most 200 characters")
        String description
) {
}
