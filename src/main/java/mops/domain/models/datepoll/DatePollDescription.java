package mops.domain.models.datepoll;


import mops.controllers.dto.InputFieldNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

public class DatePollDescription implements ValidateAble {

    private String description;


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
