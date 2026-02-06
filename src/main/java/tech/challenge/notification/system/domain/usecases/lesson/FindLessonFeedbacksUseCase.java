package tech.challenge.notification.system.domain.usecases.lesson;

import tech.challenge.notification.system.domain.entities.Feedback;
import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.domain.exceptions.LessonNotFoundException;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.valueobjects.LessonId;

import java.util.Set;

public class FindLessonFeedbacksUseCase {

    private final LessonRepository lessonRepository;

    public FindLessonFeedbacksUseCase(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Set<Feedback> execute(LessonId lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        return lesson.getFeedbacks();
    }
}
