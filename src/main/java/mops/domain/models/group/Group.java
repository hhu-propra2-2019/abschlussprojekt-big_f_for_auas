package mops.domain.models.group;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mops.domain.models.user.UserId;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public class Group {

    private final GroupMetaInf metaInf;
    private final Set<UserId> user;
}
