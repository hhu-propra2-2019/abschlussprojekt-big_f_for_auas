package mops.domain.models.group;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GroupMetaInf {

    private final GroupId id;
    private final String title;
    private final GroupVisibility visibility;

    public GroupMetaInf(String id, String title, GroupVisibility visibility) {
        this.id = new GroupId(id);
        this.title = title;
        this.visibility = visibility;
    }
}
