package tech.challenge.notification.system.domain.valueobjects;

import java.util.Objects;

public record LessonName(String value) {

    public LessonName {
        Objects.requireNonNull(value, "Lesson name cannot be null");
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("Lesson name cannot be empty");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("Lesson name must have at most 100 characters");
        }
    }

    public static LessonName of(String value) {
        return new LessonName(value.trim());
    }

}
