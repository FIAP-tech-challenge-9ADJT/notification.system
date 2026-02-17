package tech.challenge.notification.system.infrastructure.persistence.mappers;

import tech.challenge.notification.system.domain.entities.Feedback;
import tech.challenge.notification.system.infrastructure.persistence.entities.FeedbackJpaEntity;

public class FeedbackJpaMapper {

    public static FeedbackJpaEntity toJpaEntity(Feedback feedback) {
        if (feedback == null) return null;

        FeedbackJpaEntity jpaEntity = new FeedbackJpaEntity();

        if (feedback.getId() != null) {
            jpaEntity.setId(feedback.getId().value());
        }

        jpaEntity.setDescription(feedback.getDescription().value());
        jpaEntity.setScore(feedback.getScore());

        return jpaEntity;
    }

    public static Feedback toDomainEntity(FeedbackJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        return Feedback.restore(
                jpaEntity.getId(),
                jpaEntity.getDescription(),
                jpaEntity.getScore()
        );
    }
}
