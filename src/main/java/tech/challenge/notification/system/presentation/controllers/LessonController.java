package tech.challenge.notification.system.presentation.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.challenge.notification.system.application.services.LessonApplicationService;
import tech.challenge.notification.system.domain.valueobjects.LessonId;
import tech.challenge.notification.system.infrastructure.queue.FeedbackQueueService;
import tech.challenge.notification.system.presentation.dtos.feedback.CreateFeedbackDTO;
import tech.challenge.notification.system.presentation.dtos.feedback.FeedbackResponseDTO;
import tech.challenge.notification.system.presentation.dtos.lesson.CreateLessonDTO;
import tech.challenge.notification.system.presentation.dtos.lesson.LessonResponseDTO;
import tech.challenge.notification.system.presentation.dtos.lesson.UpdateLessonDTO;
import tech.challenge.notification.system.presentation.mappers.FeedbackDtoMapper;
import tech.challenge.notification.system.presentation.mappers.LessonDtoMapper;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private final LessonApplicationService lessonApplicationService;
    private final FeedbackQueueService feedbackQueueService;

    public LessonController(LessonApplicationService lessonApplicationService,
                            FeedbackQueueService feedbackQueueService) {
        this.lessonApplicationService = lessonApplicationService;
        this.feedbackQueueService = feedbackQueueService;
    }

    @PostMapping
    public ResponseEntity<LessonResponseDTO> createLesson(
            @Valid @RequestBody CreateLessonDTO dto) {

        var lesson = lessonApplicationService.createLesson(
                dto.name(),
                dto.description()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(LessonDtoMapper.toResponseDto(lesson));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonResponseDTO> getLesson(@PathVariable Long id) {
        var lesson = lessonApplicationService.getLesson(LessonId.of(id));
        return ResponseEntity.ok(LessonDtoMapper.toResponseDto(lesson));
    }

    @GetMapping("/{id}/feedbacks")
    public ResponseEntity<Set<FeedbackResponseDTO>> getFeedbacks(@PathVariable Long id) {

        var feedbacks = lessonApplicationService.getLessonFeedbacks(id);

        var response = feedbacks.stream()
                .map(FeedbackDtoMapper::toResponseDto)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonResponseDTO> updateLesson(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLessonDTO dto) {

        var lesson = lessonApplicationService.updateLesson(
                LessonId.of(id),
                dto.name(),
                dto.description()
        );

        return ResponseEntity.ok(LessonDtoMapper.toResponseDto(lesson));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonApplicationService.deleteLesson(LessonId.of(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/feedbacks")
    public ResponseEntity<Void> addFeedback(
            @PathVariable Long id,
            @Valid @RequestBody CreateFeedbackDTO dto) {

        feedbackQueueService.sendFeedback(
                id,
                dto.description(),
                dto.score()
        );

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
