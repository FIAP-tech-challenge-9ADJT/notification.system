package tech.challenge.notification.system.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.challenge.notification.system.infrastructure.persistence.entities.LessonJpaEntity;

public interface LessonJpaRepository extends JpaRepository<LessonJpaEntity, Long> {

    boolean existsById(Long id);

}
