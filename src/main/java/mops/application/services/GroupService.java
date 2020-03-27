package mops.application.services;

import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;

public interface GroupService {

    boolean groupExists(GroupId groupId);
    boolean isUserInGroup(UserId userId, GroupId groupId);
}
