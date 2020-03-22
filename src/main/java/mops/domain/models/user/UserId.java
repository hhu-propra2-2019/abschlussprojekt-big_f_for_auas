package mops.domain.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class UserId implements ValidateAble, Serializable {

    private static final long serialVersionUID = 1984021897420418247L;

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
