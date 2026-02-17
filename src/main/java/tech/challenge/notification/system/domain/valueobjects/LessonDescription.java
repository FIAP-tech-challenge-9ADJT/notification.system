package tech.challenge.notification.system.domain.valueobjects;

import java.util.Objects;

public record LessonDescription(String value) {

    public LessonDescription {
        Objects.requireNonNull(value, "Lesson description cannot be null");
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("Lesson description cannot be empty");
        }
        if (value.length() > 200) {
            throw new IllegalArgumentException("Lesson description must have at most 200 characters");
        }
    }

    public static LessonDescription of(String value) {
        return new LessonDescription(value.trim());
    }
}
