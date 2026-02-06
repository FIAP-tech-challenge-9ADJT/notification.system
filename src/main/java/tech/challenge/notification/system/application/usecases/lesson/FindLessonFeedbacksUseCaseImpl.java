package tech.challenge.notification.system.application.usecases.lesson;

import org.springframework.stereotype.Service;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.usecases.lesson.FindLessonFeedbacksUseCase;

@Service
public class FindLessonFeedbacksUseCaseImpl extends FindLessonFeedbacksUseCase {

    public FindLessonFeedbacksUseCaseImpl(LessonRepository lessonRepository) {
        super(lessonRepository);
    }
}
