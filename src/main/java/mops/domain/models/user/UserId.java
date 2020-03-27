package mops.domain.models.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UserId implements ValidateAble, Serializable {

    private static final long serialVersionUID = 1984021897420418247L;

    //Wird durch KeyCloak gesetzt
    private String id;

    /**
     * TODO: Validierung hinzufügen!
     * @return ...
     */
    @Override
    public Validation validate() {
        return Validation.noErrors();
    }

    @SuppressWarnings("checkstyle:DesignForExtension")
    @Override
    public String toString() {
        return id;
    }
}
