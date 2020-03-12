package mops.domain.models.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class User {
    @Getter private final UserId id;
}
