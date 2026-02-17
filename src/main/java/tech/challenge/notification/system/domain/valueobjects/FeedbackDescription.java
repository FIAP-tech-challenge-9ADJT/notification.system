package tech.challenge.notification.system.domain.valueobjects;

import java.util.Objects;

public record FeedbackDescription(String value) {

    public FeedbackDescription {
        Objects.requireNonNull(value, "Feedback description cannot be null");
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("Feedback description cannot be empty");
        }
        if (value.length() > 200) {
            throw new IllegalArgumentException("Feedback description must have at most 200 characters");
        }
    }

    public static FeedbackDescription of(String value) {
        return new FeedbackDescription(value.trim());
    }
}
