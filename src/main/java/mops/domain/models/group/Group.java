package mops.domain.models.group;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mops.domain.models.user.UserId;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public class Group {

    private final GroupId id;
    private final String title;
    private final Set<UserId> user;

}
