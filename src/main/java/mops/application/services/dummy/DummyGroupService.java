package mops.application.services.dummy;

        import lombok.NoArgsConstructor;
        import mops.application.services.GroupService;
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
