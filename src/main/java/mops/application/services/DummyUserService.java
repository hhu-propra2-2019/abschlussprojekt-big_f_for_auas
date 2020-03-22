package mops.application.services;

import lombok.NoArgsConstructor;
import mops.domain.models.user.UserId;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class DummyUserService implements UserService {

    @SuppressWarnings("checkstyle:DesignForExtension")
    @Override
    public boolean userExists(UserId userId) {
        return false;
    }
}
