package tech.challenge.notification.system.domain.entities;

import tech.challenge.notification.system.domain.valueobjects.FeedbackDescription;
import tech.challenge.notification.system.domain.valueobjects.FeedbackId;

import java.util.Objects;

public class Feedback {

    private final FeedbackId id;
    private final FeedbackDescription description;
    private final Integer score;

    public Feedback(FeedbackId id, FeedbackDescription description, Integer score) {
        this.id = id;
        this.description = Objects.requireNonNull(description, "Feedback description cannot be null");
        this.score = score;
    }

    public static Feedback create(String description, Integer score) {
        if (score < 0 || score > 10) {
            throw new IllegalArgumentException("Feedback score must be between 0 and 10");
        }

        return new Feedback(
                null,
                FeedbackDescription.of(description),
                score
        );
    }

    public static Feedback of(Long id, String description, Integer score) {
        return new Feedback(
                FeedbackId.of(id),
                FeedbackDescription.of(description),
                score
        );
    }

    public static Feedback restore(Long id, String description, Integer score) {
        return new Feedback(
                FeedbackId.of(id),
                FeedbackDescription.of(description),
                score
        );
    }

    public FeedbackId getId() {
        return id;
    }

    public FeedbackDescription getDescription() {
        return description;
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
