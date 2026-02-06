package tech.challenge.notification.system.presentation.dtos.feedback;

import jakarta.validation.constraints.*;

public record CreateFeedbackDTO(

        @NotBlank(message = "Description is required")
        @Size(max = 200, message = "Description must have at most 200 characters")
        String description,

        @NotNull(message = "Score is required")
        @Min(value = 0, message = "Score must be at least 0")
        @Max(value = 10, message = "Score must be at most 10")
        Integer score
) {
}
