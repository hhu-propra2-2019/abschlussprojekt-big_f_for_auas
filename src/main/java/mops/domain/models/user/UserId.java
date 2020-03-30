package mops.domain.models.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UserId implements Serializable {

    private static final long serialVersionUID = 1984021897420418247L;

    //Wird durch KeyCloak gesetzt
    private String id;

    @SuppressWarnings("checkstyle:DesignForExtension")
    @Override
    public String toString() {
        return id;
    }
}
