package tech.challenge.notification.system.domain.usecases.lesson;

import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.domain.repositories.LessonRepository;

public class CreateLessonUseCase {

    protected final LessonRepository lessonRepository;

    public CreateLessonUseCase(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson execute(String name, String description) {
        return lessonRepository.save(Lesson.create(name, description));
    }
}
