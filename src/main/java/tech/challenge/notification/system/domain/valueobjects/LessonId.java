package tech.challenge.notification.system.domain.valueobjects;

import java.util.Objects;

public record LessonId(Long value) {

    public LessonId {
        Objects.requireNonNull(value, "Lesson ID cannot be null");
        if (value <= 0) {
            throw new IllegalArgumentException("Lesson ID must be positive");
        }
    }

    public static LessonId of(Long value) {
        return new LessonId(value);
    }
}
