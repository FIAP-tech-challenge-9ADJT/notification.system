package tech.challenge.notification.system.domain.repositories;

import java.util.Optional;

import tech.challenge.notification.system.domain.entities.User;
import tech.challenge.notification.system.domain.valueobjects.Email;
import tech.challenge.notification.system.domain.valueobjects.Login;
import tech.challenge.notification.system.domain.valueobjects.UserId;

public interface UserRepository {

    Optional<User> findById(UserId id);

    Optional<User> findByLogin(Login login);

    User save(User user);

    void delete(UserId id);

    boolean existsByEmail(Email email);

    boolean existsByLogin(Login login);

    boolean existsById(UserId id);
}