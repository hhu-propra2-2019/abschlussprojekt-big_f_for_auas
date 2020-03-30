package mops.domain.repositories;

import mops.domain.models.user.User;
import mops.domain.models.user.UserId;

import java.util.Optional;

public interface UserRepository {
    Optional<User> load(UserId userId);
    void saveUserIfNotPresent(User user);
}
