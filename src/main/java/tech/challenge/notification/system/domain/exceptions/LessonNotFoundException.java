package tech.challenge.notification.system.domain.exceptions;

import tech.challenge.notification.system.domain.valueobjects.LessonId;

public class LessonNotFoundException extends DomainException {

    public LessonNotFoundException(LessonId lessonId) {
        super("User not found with ID: " + lessonId.value());
    }
}
