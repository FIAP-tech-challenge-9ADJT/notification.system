package tech.challenge.notification.system.domain.entities;

import tech.challenge.notification.system.domain.valueobjects.LessonId;
import tech.challenge.notification.system.domain.valueobjects.LessonDescription;
import tech.challenge.notification.system.domain.valueobjects.LessonName;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Lesson {

    private final LessonId id;
    private final LessonName name;
    private final LessonDescription description;
    private final Double score;
    private final Set<Feedback> feedbacks;

    public Lesson(LessonId id, LessonName name, LessonDescription description, Double score, Set<Feedback> feedbacks) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Lesson name cannot be null");
        this.description = Objects.requireNonNull(description, "Lesson description cannot be null");
        this.score = score;
        this.feedbacks = feedbacks != null ? new HashSet<>(feedbacks) : new HashSet<>();
    }

    public static Lesson create(String name, String description) {
        var lesson = new Lesson(
                null,
                LessonName.of(name),
                LessonDescription.of(description),
                0.0,
                new HashSet<>()
        );
        return lesson;
    }

    public static Lesson of(Long id, String name, String description, Double score, Set<Feedback> feedbacks) {
        return new Lesson(
                LessonId.of(id),
                LessonName.of(name),
                LessonDescription.of(description),
                score,
                feedbacks
        );
    }

    public Lesson update(String name, String description) {
        return new Lesson(this.id, LessonName.of(name), LessonDescription.of(description),
                this.score, this.feedbacks);
    }

    private Double calculateScore(Set<Feedback> feedbacks) {
        double avg = feedbacks.stream()
                .mapToInt(Feedback::getScore)
                .average()
                .orElse(0.0);

        return Math.round(avg * 10.0) / 10.0;
    }

    public Lesson addFeedback(Feedback feedback) {
        Objects.requireNonNull(feedback, "Feedback cannot be null");

        Set<Feedback> updatedFeedbacks = new HashSet<>(this.feedbacks);
        updatedFeedbacks.add(feedback);

        return new Lesson(
                this.id,
                this.name,
                this.description,
                calculateScore(updatedFeedbacks),
                updatedFeedbacks
        );
    }

    public LessonId getId() {
        return id;
    }

    public LessonName getName() {
        return name;
    }

    public LessonDescription getDescription() {
        return description;
    }

    public Double getScore() {
        return score;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
