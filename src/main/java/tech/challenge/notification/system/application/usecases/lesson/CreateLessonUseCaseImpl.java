package tech.challenge.notification.system.application.usecases.lesson;

import org.springframework.stereotype.Service;
import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.usecases.lesson.CreateLessonUseCase;

@Service
public class CreateLessonUseCaseImpl extends CreateLessonUseCase {

    public CreateLessonUseCaseImpl(LessonRepository lessonRepository) {
        super(lessonRepository);
    }

    public Lesson execute(String name, String description) {
        return super.execute(name, description);
    }
}
