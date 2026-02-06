package tech.challenge.notification.system.application.services;

import org.springframework.stereotype.Service;
import tech.challenge.notification.system.application.usecases.lesson.*;
import tech.challenge.notification.system.domain.entities.Feedback;
import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.domain.valueobjects.LessonId;

import java.util.Set;

@Service
public class LessonApplicationService {

    private final CreateLessonUseCaseImpl createLessonUseCase;
    private final FindLessonUseCaseImpl findLessonUseCase;
    private final UpdateLessonUseCaseImpl updateLessonUseCase;
    private final DeleteLessonUseCaseImpl deleteLessonUseCase;
    private final AddFeedbackToLessonUseCaseImpl addFeedbackToLessonUseCase;
    private final FindLessonFeedbacksUseCaseImpl findLessonFeedbacksUseCase;

    public LessonApplicationService(CreateLessonUseCaseImpl createLessonUseCase,
                                    FindLessonUseCaseImpl findLessonUseCase,
                                    UpdateLessonUseCaseImpl updateLessonUseCase,
                                    DeleteLessonUseCaseImpl deleteLessonUseCase,
                                    AddFeedbackToLessonUseCaseImpl addFeedbackToLessonUseCase, FindLessonFeedbacksUseCaseImpl findLessonFeedbacksUseCase) {
        this.createLessonUseCase = createLessonUseCase;
        this.findLessonUseCase = findLessonUseCase;
        this.updateLessonUseCase = updateLessonUseCase;
        this.deleteLessonUseCase = deleteLessonUseCase;
        this.addFeedbackToLessonUseCase = addFeedbackToLessonUseCase;
        this.findLessonFeedbacksUseCase = findLessonFeedbacksUseCase;
    }

    public Lesson createLesson(String name, String description) {
        return createLessonUseCase.execute(name, description);
    }

    public Lesson getLesson(LessonId lessonId) {
        return findLessonUseCase.execute(lessonId);
    }

    public Lesson updateLesson(LessonId lessonId, String name, String description) {
        return updateLessonUseCase.execute(lessonId, name, description);
    }

    public void deleteLesson(LessonId lessonId) {
        deleteLessonUseCase.execute(lessonId);
    }

    public Lesson addFeedbackToLesson(Long lessonId, String description, Integer score) {
        return addFeedbackToLessonUseCase.execute(
                LessonId.of(lessonId),
                description,
                score
        );
    }

    public Set<Feedback> getLessonFeedbacks(Long lessonId) {
        return findLessonFeedbacksUseCase.execute(LessonId.of(lessonId));
    }
}
