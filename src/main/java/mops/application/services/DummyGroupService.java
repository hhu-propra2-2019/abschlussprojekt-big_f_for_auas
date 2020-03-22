package mops.application.services;

        import lombok.NoArgsConstructor;
        import mops.domain.models.group.GroupId;
        import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class DummyGroupService implements GroupService {

    @SuppressWarnings("checkstyle:DesignForExtension")
    @Override
    public boolean groupExists(GroupId groupId) {
        return false;
    }
}
