package tech.challenge.notification.system.domain.usecases.user;

import tech.challenge.notification.system.domain.entities.User;
import tech.challenge.notification.system.domain.exceptions.UserNotFoundException;
import tech.challenge.notification.system.domain.repositories.UserRepository;
import tech.challenge.notification.system.domain.valueobjects.UserId;

public class FindUserUseCase {
    
    private final UserRepository userRepository;
    
    public FindUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User execute(UserId userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    }
}