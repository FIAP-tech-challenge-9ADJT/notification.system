package tech.challenge.notification.system.infrastructure.persistence.repositories;

import org.springframework.stereotype.Repository;
import tech.challenge.notification.system.domain.entities.Lesson;
import tech.challenge.notification.system.domain.repositories.LessonRepository;
import tech.challenge.notification.system.domain.valueobjects.LessonId;
import tech.challenge.notification.system.infrastructure.persistence.mappers.LessonJpaMapper;

import java.util.Optional;

@Repository
public class LessonRepositoryImpl implements LessonRepository {

    private final LessonJpaRepository lessonJpaRepository;

    public LessonRepositoryImpl(LessonJpaRepository lessonJpaRepository) {
        this.lessonJpaRepository = lessonJpaRepository;
    }

    @Override
    public Optional<Lesson> findById(LessonId id) {
        return lessonJpaRepository.findById(id.value())
                .map(LessonJpaMapper::toDomainEntity);
    }

    @Override
    public Lesson save(Lesson lesson) {
        var jpaEntity = LessonJpaMapper.toJpaEntity(lesson);
        var savedEntity = lessonJpaRepository.save(jpaEntity);
        return LessonJpaMapper.toDomainEntity(savedEntity);
    }

    @Override
    public boolean existsById(LessonId id) {
        return lessonJpaRepository.existsById(id.value());
    }

    @Override
    public void deleteById(LessonId id) {
        lessonJpaRepository.deleteById(id.value());
    }
}
