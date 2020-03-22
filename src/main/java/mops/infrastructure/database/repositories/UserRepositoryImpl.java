package mops.infrastructure.database.repositories;

import lombok.NoArgsConstructor;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.domain.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@SuppressWarnings("checkstyle:DesignForExtension")
@Repository
@NoArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> load(UserId userId) {
        return Optional.empty();
    }
}
