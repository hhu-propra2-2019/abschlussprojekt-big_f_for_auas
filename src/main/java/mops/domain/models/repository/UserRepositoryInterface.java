package mops.domain.models.repository;

import mops.domain.models.user.User;
import mops.domain.models.user.UserId;

public interface UserRepositoryInterface {

  User getById(UserId id);

}
