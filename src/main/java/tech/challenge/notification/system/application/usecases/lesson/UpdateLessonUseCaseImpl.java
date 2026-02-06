package tech.challenge.notification.system.application.usecases.lesson;

import org.springframework.stereotype.Service;
import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.usecases.lesson.UpdateLessonUseCase;
import tech.challenge.notification.system.domain.valueobjects.LessonId;

@Service
public class UpdateLessonUseCaseImpl extends UpdateLessonUseCase {
    public UpdateLessonUseCaseImpl(LessonRepository lessonRepository) {
        super(lessonRepository);
    }

    public Lesson execute(LessonId lessonId, String name, String description) {
        return super.execute(lessonId, name, description);
    }
}
