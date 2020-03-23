package mops.application.services;

import mops.domain.models.user.UserId;

public interface UserService {

    boolean userExists(UserId userId);
}
