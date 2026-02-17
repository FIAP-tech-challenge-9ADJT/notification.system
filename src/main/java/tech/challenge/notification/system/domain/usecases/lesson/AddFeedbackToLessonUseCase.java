package tech.challenge.notification.system.domain.usecases.lesson;

import tech.challenge.notification.system.domain.entities.Feedback;
import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.domain.exceptions.LessonNotFoundException;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.valueobjects.LessonId;

public class AddFeedbackToLessonUseCase {

    private final LessonRepository lessonRepository;

    public AddFeedbackToLessonUseCase(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson execute(LessonId lessonId, String description, Integer score) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        Feedback feedback = Feedback.create(description, score);

        Lesson updatedLesson = lesson.addFeedback(feedback);

        return lessonRepository.save(updatedLesson);
    }
}
