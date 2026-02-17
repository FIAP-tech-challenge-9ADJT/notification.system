package tech.challenge.notification.system.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tech.challenge.notification.system.domain.repositories.*;
import tech.challenge.notification.system.infrastructure.persistence.repositories.*;

@Configuration
public class RepositoryConfig {
    
    @Bean
    public UserRepository userRepository(UserJpaRepository userJpaRepository) {
        return new UserRepositoryImpl(userJpaRepository);
    }
    
    @Bean
    public RoleRepository roleRepository(RoleJpaRepository roleJpaRepository) {
        return new RoleRepositoryImpl(roleJpaRepository);
    }

    @Bean
    public LessonRepository lessonRepository(LessonJpaRepository lessonJpaRepository) {
        return new LessonRepositoryImpl(lessonJpaRepository);
    }
}