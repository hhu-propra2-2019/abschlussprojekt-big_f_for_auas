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

    private String userId;

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
