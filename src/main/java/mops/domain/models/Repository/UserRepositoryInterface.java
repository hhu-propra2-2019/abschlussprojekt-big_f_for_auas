package mops.domain.models.Repository;

import mops.domain.models.User.User;
import mops.domain.models.User.UserId;

public interface UserRepositoryInterface {

  User getById(UserId id);

}
