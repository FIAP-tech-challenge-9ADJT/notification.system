package tech.challenge.notification.system.domain.valueobjects;

import java.util.Objects;

public record FeedbackId(Long value) {

    public FeedbackId {
        Objects.requireNonNull(value, "Feedback ID cannot be null");
        if (value <= 0) {
            throw new IllegalArgumentException("Feedback ID must be positive");
        }
    }

    public static FeedbackId of(Long value) {
        return new FeedbackId(value);
    }

}
