package tech.challenge.notification.system.domain.usecases.lesson;

import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.domain.exceptions.LessonNotFoundException;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.valueobjects.LessonId;

public class FindLessonUseCase {

    private final LessonRepository lessonRepository;

    public FindLessonUseCase(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson execute(LessonId lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));
    }
}
