package mops.database;

import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.group.GroupMetaInf;
import mops.domain.models.group.GroupVisibility;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class DatabaseTestUtil {

    private static final Random RANDOM = new Random();

    private DatabaseTestUtil() {
    }

    public static Group createGroup(int users) {
        final Set<User> participants =
                IntStream.range(0, users)
                        .mapToObj(i -> new User(new UserId(Integer.toString(i))))
                        .collect(Collectors.toSet());
        return new Group(new GroupMetaInf(new GroupId("1"), "Testgruppe", GroupVisibility.PRIVATE), participants);
    }

    public static User createRandomUser() {
        return new User(new UserId(Integer.toString(RANDOM.nextInt())));
    }
}
