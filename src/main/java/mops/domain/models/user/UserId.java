package mops.domain.models.user;

import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

public class UserId implements ValidateAble {

    /**
     * ...
     * @return
     */
    @Override
    public Validation validate() {
        String errorMessage = "User creator is not valid. DATE_POLL_CREATOR contains problems:\n";
        Validation validation = Validation.noErrors();
        return validation;

    }
}
