package mops.domain.models.datepoll;


import lombok.Value;
import mops.controllers.dtos.InputFieldNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

@Value
public class DatePollDescription implements ValidateAble {

    private String description;


    /**
     * ...
     * @return
     */
    @Override
    public Validation validate() {
        Validation validation = Validation.noErrors();
        String errorMessage = "No description specified. DATE_POLL_DESCRIPTION is not present.";
        if (description == null || description.equals(" ") || description.length() == 0) {
            validation = new Validation(InputFieldNames.DATE_POLL_LOCATION, errorMessage);
        }
        return validation;
    }
}
