package tech.challenge.notification.system.domain.repositories;

import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.domain.valueobjects.LessonId;

import java.util.Optional;

public interface LessonRepository {

    Optional<Lesson> findById(LessonId id);
    Lesson save(Lesson lesson);
    boolean existsById(LessonId id);
    void deleteById(LessonId id);

}
