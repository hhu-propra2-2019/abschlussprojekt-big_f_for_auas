package mops.domain.repositories;

import mops.domain.models.user.User;
import mops.domain.models.user.UserId;

public interface UserRepository {
    User getUserById(UserId userId);
}
