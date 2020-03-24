package mops.application.services.implementation;

import mops.application.services.UserService;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.repositories.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final transient UserRepositoryImpl userRepository;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }
    /**
     * Methode zum Überprüfen, ob ein User in der Datenbank existiert.
     * @param userId Id des zu überprüfenden users.
     * @return boolean, das angibt, ob der User existiert oder nicht.
     */
    @Override
    public boolean userExists(UserId userId) {
        return userRepository.existsById(userId);
    }
}
