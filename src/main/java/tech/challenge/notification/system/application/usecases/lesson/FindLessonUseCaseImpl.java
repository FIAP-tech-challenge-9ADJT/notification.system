package tech.challenge.notification.system.application.usecases.lesson;

import org.springframework.stereotype.Service;
import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.usecases.lesson.FindLessonUseCase;
import tech.challenge.notification.system.domain.valueobjects.LessonId;

@Service
public class FindLessonUseCaseImpl extends FindLessonUseCase {

    public FindLessonUseCaseImpl(LessonRepository lessonRepository) {
        super(lessonRepository);
    }

    public Lesson execute(LessonId lessonId) {
        return super.execute(lessonId);
    }
}
