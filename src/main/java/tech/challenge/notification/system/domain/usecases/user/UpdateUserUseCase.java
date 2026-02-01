package tech.challenge.notification.system.domain.usecases.user;

import tech.challenge.notification.system.domain.entities.User;
import tech.challenge.notification.system.domain.exceptions.UserNotFoundException;
import tech.challenge.notification.system.domain.repositories.UserRepository;
import tech.challenge.notification.system.domain.valueobjects.UserId;

public class UpdateUserUseCase {
    
    private final UserRepository userRepository;
    
    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User execute(UserId userId, String name, String email) {
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        
        User updatedUser = existingUser.updateProfile(name, email);
        
        return userRepository.save(updatedUser);
    }
}