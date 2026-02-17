package tech.challenge.notification.system.domain.usecases.lesson;

import tech.challenge.notification.system.domain.exceptions.LessonNotFoundException;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.valueobjects.LessonId;

public class DeleteLessonUseCase {

    private final LessonRepository lessonRepository;

    public DeleteLessonUseCase(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public void execute(LessonId id) {
        if (!lessonRepository.existsById(id)) {
            throw new LessonNotFoundException(id);
        }

        lessonRepository.deleteById(id);
    }
}
