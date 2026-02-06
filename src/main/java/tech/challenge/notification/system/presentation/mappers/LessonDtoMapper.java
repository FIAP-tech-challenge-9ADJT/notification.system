package tech.challenge.notification.system.presentation.mappers;

import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.presentation.dtos.lesson.LessonResponseDTO;

import java.util.stream.Collectors;

public class LessonDtoMapper {

    public static LessonResponseDTO toResponseDto(Lesson lesson) {
        return new LessonResponseDTO(
                lesson.getId() != null ? lesson.getId().value() : null,
                lesson.getName().value(),
                lesson.getDescription().value(),
                lesson.getScore(),
                lesson.getFeedbacks().stream()
                        .map(FeedbackDtoMapper::toResponseDto)
                        .collect(Collectors.toSet())
        );
    }
}
