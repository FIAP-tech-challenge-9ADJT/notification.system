package tech.challenge.notification.system.application.usecases.lesson;

import org.springframework.stereotype.Service;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.usecases.lesson.DeleteLessonUseCase;
import tech.challenge.notification.system.domain.valueobjects.LessonId;

@Service
public class DeleteLessonUseCaseImpl extends DeleteLessonUseCase {
    public DeleteLessonUseCaseImpl(LessonRepository lessonRepository) {
        super(lessonRepository);
    }

    public void execute(LessonId lessonId) {
        super.execute(lessonId);
    }
}
