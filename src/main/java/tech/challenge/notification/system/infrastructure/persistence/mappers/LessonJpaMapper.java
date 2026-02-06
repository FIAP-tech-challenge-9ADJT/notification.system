package tech.challenge.notification.system.infrastructure.persistence.mappers;

import tech.challenge.notification.system.domain.entities.Feedback;
import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.infrastructure.persistence.entities.FeedbackJpaEntity;
import tech.challenge.notification.system.infrastructure.persistence.entities.LessonJpaEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public class LessonJpaMapper {

    public static LessonJpaEntity toJpaEntity(Lesson lesson) {
        if (lesson == null) return null;

        LessonJpaEntity jpaEntity = new LessonJpaEntity();

        if (lesson.getId() != null) {
            jpaEntity.setId(lesson.getId().value());
        }

        jpaEntity.setName(lesson.getName().value());
        jpaEntity.setDescription(lesson.getDescription().value());
        jpaEntity.setScore(lesson.getScore());

        if (lesson.getFeedbacks() != null && !lesson.getFeedbacks().isEmpty()) {
            HashSet<FeedbackJpaEntity> feedbackSet =
                    lesson.getFeedbacks().stream()
                            .map(FeedbackJpaMapper::toJpaEntity)
                            .collect(Collectors.toCollection(HashSet::new));
            jpaEntity.setFeedbacks(feedbackSet);
        }

        return jpaEntity;
    }

    public static Lesson toDomainEntity(LessonJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        HashSet<Feedback> feedbacksHashSet = new HashSet<>();

        if (jpaEntity.getFeedbacks() != null && !jpaEntity.getFeedbacks().isEmpty()) {
            jpaEntity.getFeedbacks().stream()
                    .map(FeedbackJpaMapper::toDomainEntity)
                    .filter(Objects::nonNull)
                    .forEach(feedbacksHashSet::add);
        }

        return Lesson.of(
                jpaEntity.getId(),
                jpaEntity.getName(),
                jpaEntity.getDescription(),
                jpaEntity.getScore(),
                feedbacksHashSet
        );
    }
}
