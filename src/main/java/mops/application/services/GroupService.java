package mops.application.services;

import mops.domain.models.group.GroupId;

public interface GroupService {

    boolean groupExists(GroupId groupId);
}
