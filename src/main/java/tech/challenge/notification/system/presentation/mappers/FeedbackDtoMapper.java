package tech.challenge.notification.system.presentation.mappers;

import tech.challenge.notification.system.domain.entities.Feedback;
import tech.challenge.notification.system.presentation.dtos.feedback.FeedbackResponseDTO;

public class FeedbackDtoMapper {

    public static FeedbackResponseDTO toResponseDto(Feedback feedback) {
        return new FeedbackResponseDTO(
                feedback.getId() != null ? feedback.getId().value() : null,
                feedback.getDescription().value(),
                feedback.getScore()
        );
    }
}
