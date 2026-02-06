package tech.challenge.notification.system.presentation.dtos.feedback;

public record FeedbackResponseDTO(
        Long id,
        String description,
        Integer score
) {
}
