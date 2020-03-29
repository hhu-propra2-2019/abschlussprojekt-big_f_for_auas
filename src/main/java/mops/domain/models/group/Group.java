package mops.domain.models.group;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mops.domain.models.user.User;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public class Group {

    private final GroupMetaInf metaInf;
    private final Set<User> user;
}
