package tech.challenge.notification.system.presentation.dtos.lesson;

import tech.challenge.notification.system.presentation.dtos.feedback.FeedbackResponseDTO;

import java.util.Set;

public record LessonResponseDTO(
        Long id,
        String name,
        String description,
        Double score,
        Set<FeedbackResponseDTO> feedbacks
) {
}
