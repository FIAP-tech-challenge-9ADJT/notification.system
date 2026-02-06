package tech.challenge.notification.system.application.usecases.lesson;

import org.springframework.stereotype.Service;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.usecases.lesson.AddFeedbackToLessonUseCase;

@Service
public class AddFeedbackToLessonUseCaseImpl extends AddFeedbackToLessonUseCase {

    public AddFeedbackToLessonUseCaseImpl(LessonRepository lessonRepository) {
        super(lessonRepository);
    }
}
