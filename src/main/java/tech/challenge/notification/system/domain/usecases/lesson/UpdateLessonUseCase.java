package tech.challenge.notification.system.domain.usecases.lesson;

import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.domain.exceptions.LessonNotFoundException;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.valueobjects.LessonId;

public class UpdateLessonUseCase {

    private final LessonRepository lessonRepository;

    public UpdateLessonUseCase(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson execute(LessonId lessonId, String name, String description) {
        Lesson existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        Lesson updatedLesson = existingLesson.update(name, description);

        return lessonRepository.save(updatedLesson);
    }
}
