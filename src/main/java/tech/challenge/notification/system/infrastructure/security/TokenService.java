package tech.challenge.notification.system.infrastructure.security;

import tech.challenge.notification.system.infrastructure.persistence.entities.UserJpaEntity;

public interface TokenService {
    String generateToken(UserJpaEntity user);
    String verifyToken(String token);
}