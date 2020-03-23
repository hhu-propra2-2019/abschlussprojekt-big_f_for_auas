package mops.domain.models.group;

import lombok.Value;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

import java.io.Serializable;

@Value
public class GroupId  implements ValidateAble, Serializable {

    private static final long serialVersionUID = 435345209986L;

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
